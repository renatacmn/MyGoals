package br.com.mygoals.base.repository

import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.dao.RuleDao
import br.com.mygoals.base.repository.models.SavingsRules
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RulesRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val ruleDao: RuleDao
) {

    fun getSavingsRules(): Single<SavingsRules> {
        return api.getSavingsRules()
    }

}
