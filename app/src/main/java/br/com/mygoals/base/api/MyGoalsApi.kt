package br.com.mygoals.base.api

import br.com.mygoals.base.api.models.Feed
import br.com.mygoals.base.api.models.SavingsGoals
import br.com.mygoals.base.api.models.SavingsRules
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MyGoalsApi {

    @GET("savingsgoals")
    fun getSavingsGoals(): Single<SavingsGoals>

    @GET("savingsrules")
    fun getSavingsRules(): Single<SavingsRules>

    @GET("savingsgoals/{id}/feed")
    fun getFeed(
        @Path("id") id: Int
    ): Single<Feed>

}
