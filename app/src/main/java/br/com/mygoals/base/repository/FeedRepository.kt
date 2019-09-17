package br.com.mygoals.base.repository

import br.com.mygoals.base.BaseRepository
import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.FeedItemDao
import br.com.mygoals.base.repository.dao.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.models.Feed
import io.reactivex.Single
import java.util.Date
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val feedItemDao: FeedItemDao
) : BaseRepository() {

    fun getFeed(goalId: Int) = loadFromDbRefreshingIfNecessary(goalId)

    private fun loadFromDbRefreshingIfNecessary(goalId: Int): Single<Feed> {
        return feedItemDao.hasFeedItems(goalId, getMaxRefreshTime())
            .flatMap {
                // Exists on DB
                feedItemDao.loadFeedItems(goalId)
                    .map { Feed(it.toDomainModel()) }
            }
            .onErrorResumeNext {
                // Doesn't exist on DB or couldn't be loaded
                api.getFeed(goalId)
                    .map { it.toDomainModel() }
                    .flatMap { feed ->
                        feed.feed?.let { feedItems ->
                            feedItemDao.saveFeedItems(goalId, feedItems.toEntity(Date(), goalId))
                        }
                        feedItemDao.loadFeedItems(goalId)
                            .map { Feed(it.toDomainModel()) }
                    }
            }
    }

}
