package br.com.mygoals.base.repository

import br.com.mygoals.base.BaseRepository
import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.api.models.SavingsGoalsApiModel
import br.com.mygoals.base.repository.dao.GoalDao
import br.com.mygoals.base.repository.dao.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.dao.models.GoalEntity
import br.com.mygoals.base.repository.models.SavingsGoals
import br.com.mygoals.base.repository.util.RepositoryUtil
import br.com.mygoals.util.executors.Executors
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

class GoalsRepositoryBkp @Inject constructor(
    private val api: MyGoalsApi,
    private val goalDao: GoalDao,
    private val repositoryUtil: RepositoryUtil,
    private val executors: Executors
) : BaseRepository() {

    private lateinit var listener: Listener

    fun getSavingsGoals(listener: Listener) {
        this.listener = listener
        loadFromDbRefreshingIfNecessary()
    }

    // Private methods

    private fun loadFromDbRefreshingIfNecessary() {
        Timber.d("Check if exists")
        add(
            goalDao.hasGoals(repositoryUtil.getMaxRefreshTime())
                .subscribeOn(executors.diskIO())
                .subscribe(
                    (this::onCheckIfExistsSuccess),
                    (this::onCheckIfExistsError)
                )
        )
    }

    private fun onCheckIfExistsSuccess(goalEntity: GoalEntity?) {
        val exists = goalEntity != null
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
            api.getSavingsGoals()
                .subscribeOn(executors.networkIO())
                .observeOn(executors.diskIO())
                .subscribe(
                    (this::onLoadFromApiSuccess),
                    (this::onLoadFromApiError)
                )
        )
    }

    private fun onLoadFromApiSuccess(data: SavingsGoalsApiModel) {
        Timber.d("> Loaded successfully from API. Will save on DB")
        data.toDomainModel()?.savingsGoals?.let { goals ->
            goals.map { it.lastRefresh = Date() }
            goalDao.saveGoals(goals.mapNotNull { it.toEntity() })
        }
        loadFromDb()
    }

    private fun onLoadFromApiError(error: Throwable) {
        Timber.d("> Error loading from API. Will show error state")
        error.printStackTrace()
        listener.onGoalsError(error)
    }

    private fun loadFromDb() {
        Timber.d("Load from DB")
        add(
            goalDao.loadGoals()
                .subscribeOn(executors.diskIO())
                .observeOn(executors.mainThread())
                .subscribe(
                    (this::onLoadFromDbSuccess),
                    (this::onLoadFromDbError)
                )
        )
    }

    private fun onLoadFromDbSuccess(goals: List<GoalEntity>) {
        Timber.d("> Loaded successfully from DB. Will send to view")
        val savingsGoals = SavingsGoals(goals.mapNotNull { it.toDomainModel() })
        listener.onGoalsSuccess(savingsGoals)
    }

    private fun onLoadFromDbError(error: Throwable) {
        Timber.d("> Error loading from DB. Will show error state\n>>${error.message}")
        listener.onGoalsError(error)
    }

    interface Listener {
        fun onGoalsSuccess(savingsGoals: SavingsGoals)
        fun onGoalsError(throwable: Throwable)
    }

}
