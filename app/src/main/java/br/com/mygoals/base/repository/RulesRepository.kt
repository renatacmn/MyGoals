package br.com.mygoals.base.repository

import br.com.mygoals.base.BaseRepository
import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.api.models.SavingsRulesApiModel
import br.com.mygoals.base.repository.dao.RuleDao
import br.com.mygoals.base.repository.dao.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.dao.models.RuleEntity
import br.com.mygoals.base.repository.models.SavingsRules
import br.com.mygoals.base.repository.util.RepositoryUtil
import br.com.mygoals.util.executors.Executors
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RulesRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val ruleDao: RuleDao,
    private val repositoryUtil: RepositoryUtil,
    private val executors: Executors
) : BaseRepository() {

    private lateinit var listener: Listener

    fun getSavingsRules(listener: Listener) {
        this.listener = listener
        loadFromDbRefreshingIfNecessary()
    }

    // Private methods

    private fun loadFromDbRefreshingIfNecessary() {
        add(
            ruleDao.hasRules(repositoryUtil.getMaxRefreshTime())
                .subscribeOn(executors.diskIO())
                .subscribe(
                    (this::onCheckIfExistsSuccess),
                    (this::onCheckIfExistsError)
                )
        )
    }

    private fun onCheckIfExistsSuccess(ruleEntity: RuleEntity?) {
        val exists = ruleEntity != null
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
        add(
            api.getSavingsRules()
                .subscribeOn(executors.networkIO())
                .observeOn(executors.diskIO())
                .subscribe(
                    (this::onLoadFromApiSuccess),
                    (this::onLoadFromApiError)
                )
        )
    }

    private fun onLoadFromApiSuccess(data: SavingsRulesApiModel) {
        data.toDomainModel()?.savingsRules?.let { rules ->
            rules.map { it.lastRefresh = Date() }
            ruleDao.saveRules(rules.mapNotNull { it.toEntity() })
        }
        loadFromDb()
    }

    private fun onLoadFromApiError(error: Throwable) {
        error.printStackTrace()
        listener.onRulesError(error)
    }

    private fun loadFromDb() {
        add(
            ruleDao.loadRules()
                .subscribeOn(executors.diskIO())
                .observeOn(executors.mainThread())
                .subscribe(
                    (this::onLoadFromDbSuccess),
                    (this::onLoadFromDbError)
                )
        )
    }

    private fun onLoadFromDbSuccess(rules: List<RuleEntity>) {
        val savingsRules = SavingsRules(rules.mapNotNull { it.toDomainModel() })
        listener.onRulesSuccess(savingsRules)
    }

    private fun onLoadFromDbError(error: Throwable) {
        listener.onRulesError(error)
    }

    interface Listener {
        fun onRulesSuccess(savingsRules: SavingsRules)
        fun onRulesError(throwable: Throwable)
    }

}
