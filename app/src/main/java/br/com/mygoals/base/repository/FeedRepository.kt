package br.com.mygoals.base.repository

import br.com.mygoals.base.BaseRepository
import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.api.models.FeedApiModel
import br.com.mygoals.base.repository.dao.FeedItemDao
import br.com.mygoals.base.repository.dao.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.dao.models.FeedItemEntity
import br.com.mygoals.base.repository.models.Feed
import br.com.mygoals.util.executors.Executors
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val feedItemDao: FeedItemDao,
    private val repositoryUtil: RepositoryUtil,
    private val executors: Executors
) : BaseRepository() {

    private lateinit var listener: Listener
    private var goalId: Int? = null

    fun getFeed(listener: Listener, id: Int) {
        this.listener = listener
        this.goalId = id
        loadFromDbRefreshingIfNecessary()
    }

    // Private methods

    private fun loadFromDbRefreshingIfNecessary() {
        add(
            feedItemDao.hasFeedItems(repositoryUtil.getMaxRefreshTime())
                .subscribeOn(executors.diskIO())
                .subscribe(
                    (this::onCheckIfExistsSuccess),
                    (this::onCheckIfExistsError)
                )
        )
    }

    private fun onCheckIfExistsSuccess(feedItemEntity: FeedItemEntity?) {
        val exists = feedItemEntity != null
        if (!exists) {
            loadFromApi()
        } else {
            loadFromDb()
        }
    }

    private fun onCheckIfExistsError(error: Throwable) {
        error.printStackTrace()
        loadFromApi()
    }

    private fun loadFromApi() {
        goalId?.let { goalId ->
            add(
                api.getFeed(goalId)
                    .subscribeOn(executors.networkIO())
                    .observeOn(executors.diskIO())
                    .subscribe(
                        (this::onLoadFromApiSuccess),
                        (this::onLoadFromApiError)
                    )
            )
        }
    }

    private fun onLoadFromApiSuccess(data: FeedApiModel) {
        data.toDomainModel()?.feed?.let { feedItems ->
            feedItems.map { it.lastRefresh = Date() }
            feedItemDao.saveFeedItems(feedItems.mapNotNull { it.toEntity() })
        }
        loadFromDb()
    }

    private fun onLoadFromApiError(error: Throwable) {
        error.printStackTrace()
        listener.onFeedError(error)
    }

    private fun loadFromDb() {
        add(
            feedItemDao.loadFeedItems()
                .subscribeOn(executors.diskIO())
                .observeOn(executors.mainThread())
                .subscribe(
                    (this::onLoadFromDbSuccess),
                    (this::onLoadFromDbError)
                )
        )
    }

    private fun onLoadFromDbSuccess(feedItems: List<FeedItemEntity>) {
        val feed = Feed(feedItems.mapNotNull { it.toDomainModel() })
        listener.onFeedSuccess(feed)
    }

    private fun onLoadFromDbError(error: Throwable) {
        listener.onFeedError(error)
    }

    interface Listener {
        fun onFeedSuccess(feed: Feed)
        fun onFeedError(throwable: Throwable)
    }

}
