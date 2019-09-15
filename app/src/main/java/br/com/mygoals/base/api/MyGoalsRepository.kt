package br.com.mygoals.base.api

import br.com.mygoals.base.api.models.Feed
import br.com.mygoals.base.api.models.SavingsGoals
import br.com.mygoals.base.api.models.SavingsRules
import io.reactivex.Single
import javax.inject.Inject

class MyGoalsRepository @Inject constructor(
    private val api: MyGoalsApi
) {

    fun getSavingsGoals(): Single<SavingsGoals> {
        return api.getSavingsGoals()
    }

    fun getSavingsRules(): Single<SavingsRules> {
        return api.getSavingsRules()
    }

    fun getFeed(id: Int): Single<Feed> {
        return api.getFeed(id)
    }

}
