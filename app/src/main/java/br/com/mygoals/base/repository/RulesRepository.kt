package br.com.mygoals.base.repository

import br.com.mygoals.base.BaseRepository
import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.RuleDao
import br.com.mygoals.base.repository.dao.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.models.SavingsRules
import io.reactivex.Single
import java.util.Date
import javax.inject.Inject

class RulesRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val ruleDao: RuleDao
) : BaseRepository() {

    fun getSavingsRules() = loadFromDbRefreshingIfNecessary()

    private fun loadFromDbRefreshingIfNecessary(): Single<SavingsRules> {
        return ruleDao.hasRules(getMaxRefreshTime())
            .flatMap {
                // Exists on DB
                ruleDao.loadRules()
                    .map { SavingsRules(it.toDomainModel()) }
            }
            .onErrorResumeNext {
                // Doesn't exist on DB or couldn't be loaded
                api.getSavingsRules()
                    .map { it.toDomainModel() }
                    .flatMap { savingsRules ->
                        savingsRules.savingsRules?.let { rulesList ->
                            ruleDao.saveRules(rulesList.toEntity(Date()))
                        }
                        ruleDao.loadRules()
                            .map { SavingsRules(it.toDomainModel()) }
                    }
            }
    }

}
