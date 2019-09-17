package br.com.mygoals.base.repository

import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.dao.GoalDao
import br.com.mygoals.base.repository.models.SavingsRules
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RulesRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val goalDao: GoalDao
) {

    fun getSavingsRules(): Single<SavingsRules> {
        return api.getSavingsRules()
    }

}
