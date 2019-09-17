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
import br.com.mygoals.util.executors.Executors
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

class RulesRepositoryBkp @Inject constructor(
    private val api: MyGoalsApi,
    private val ruleDao: RuleDao,
    private val executors: Executors
) : BaseRepository() {

    private lateinit var listener: Listener

    fun getSavingsRules(listener: Listener) {
        this.listener = listener
        loadFromDbRefreshingIfNecessary()
    }

    // Private methods

    private fun loadFromDbRefreshingIfNecessary() {
        Timber.d("Check if exists")
        add(
            ruleDao.hasRules(getMaxRefreshTime())
                .subscribeOn(executors.diskIO())
                .observeOn(executors.diskIO())
                .subscribe(
                    (this::onCheckIfExistsSuccess),
                    (this::onCheckIfExistsError)
                )
        )
    }

    private fun onCheckIfExistsSuccess(ruleEntity: RuleEntity?) {
        val exists = ruleEntity != null
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
        Timber.d("> Loaded successfully from API. Will save on DB")
        data.toDomainModel()?.savingsRules?.let { rules ->
            ruleDao.saveRules(rules.mapNotNull { it.toEntity(Date()) })
        }
        loadFromDb()
    }

    private fun onLoadFromApiError(error: Throwable) {
        Timber.d("> Error loading from API. Will show error state\n>>${error.message}")
        error.printStackTrace()
        listener.onRulesError(error)
    }

    private fun loadFromDb() {
        Timber.d("Load from DB")
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
        Timber.d("> Loaded successfully from DB. Will send to view")
        val savingsRules = SavingsRules(rules.mapNotNull { it.toDomainModel() })
        listener.onRulesSuccess(savingsRules)
    }

    private fun onLoadFromDbError(error: Throwable) {
        Timber.d("> Error loading from DB. Will show error state\n>>${error.message}")
        listener.onRulesError(error)
    }

    interface Listener {
        fun onRulesSuccess(savingsRules: SavingsRules)
        fun onRulesError(throwable: Throwable)
    }

}
