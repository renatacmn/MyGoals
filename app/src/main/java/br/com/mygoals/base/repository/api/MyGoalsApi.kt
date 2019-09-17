package br.com.mygoals.base.repository.api

import br.com.mygoals.base.repository.api.models.FeedApiModel
import br.com.mygoals.base.repository.api.models.SavingsGoalsApiModel
import br.com.mygoals.base.repository.api.models.SavingsRulesApiModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MyGoalsApi {

    @GET("savingsgoals")
    fun getSavingsGoals(): Single<SavingsGoalsApiModel>

    @GET("savingsrules")
    fun getSavingsRules(): Single<SavingsRulesApiModel>

    @GET("savingsgoals/{id}/feed")
    fun getFeed(
        @Path("id") id: Int
    ): Single<FeedApiModel>

}
