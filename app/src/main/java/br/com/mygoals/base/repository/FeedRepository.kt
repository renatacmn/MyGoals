package br.com.mygoals.base.repository

import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.models.Feed
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepository @Inject constructor(
    private val api: MyGoalsApi
) {

    fun getFeed(id: Int): Single<Feed> {
        return api.getFeed(id)
    }

}
