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
import br.com.mygoals.base.repository.util.RepositoryUtil
import br.com.mygoals.util.executors.Executors
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val feedItemDao: FeedItemDao,
    private val repositoryUtil: RepositoryUtil,
    private val executors: Executors
) : BaseRepository() {

    private lateinit var listener: Listener
    private var goalId: Int = -1

    fun getFeed(listener: Listener, id: Int) {
        this.listener = listener
        this.goalId = id
        if (goalId != -1) {
            loadFromDbRefreshingIfNecessary()
        }
    }

    // Private methods

    private fun loadFromDbRefreshingIfNecessary() {
        Timber.d("Check if exists")
        add(
            feedItemDao.hasFeedItems(goalId, repositoryUtil.getMaxRefreshTime())
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
            Timber.d("> Doesn't exist. Will load from API")
            loadFromApi()
        } else {
            Timber.d("> Exists. Will load from DB")
            loadFromDb()
        }
    }

    private fun onCheckIfExistsError(error: Throwable) {
        Timber.d("> Error while checking if exists. Will load from API\n>>${error.message}")
        error.printStackTrace()
        loadFromApi()
    }

    private fun loadFromApi() {
        Timber.d("Load from API")
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

    private fun onLoadFromApiSuccess(data: FeedApiModel) {
        Timber.d("> Loaded successfully from API. Will save on DB")
        data.toDomainModel()?.feed?.let { feedItems ->
            feedItemDao.saveFeedItems(goalId, feedItems.mapNotNull {
                it.toEntity(Date(), goalId)
            })
        }
        loadFromDb()
    }

    private fun onLoadFromApiError(error: Throwable) {
        Timber.d("> Error loading from API. Will show error state\n>>${error.message}")
        error.printStackTrace()
        listener.onFeedError(error)
    }

    private fun loadFromDb() {
        Timber.d("Load from DB")
        add(
            feedItemDao.loadFeedItems(goalId)
                .subscribeOn(executors.diskIO())
                .observeOn(executors.mainThread())
                .subscribe(
                    (this::onLoadFromDbSuccess),
                    (this::onLoadFromDbError)
                )
        )
    }

    private fun onLoadFromDbSuccess(feedItems: List<FeedItemEntity>) {
        Timber.d("> Loaded successfully from DB. Will send to view")
        val feed = Feed(feedItems.mapNotNull { it.toDomainModel() })
        listener.onFeedSuccess(feed)
    }

    private fun onLoadFromDbError(error: Throwable) {
        Timber.d("> Error loading from DB. Will show error state\n>>${error.message}")
        listener.onFeedError(error)
    }

    interface Listener {
        fun onFeedSuccess(feed: Feed)
        fun onFeedError(throwable: Throwable)
    }

}
