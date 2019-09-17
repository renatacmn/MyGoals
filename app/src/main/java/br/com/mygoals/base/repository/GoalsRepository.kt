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
import br.com.mygoals.util.executors.Executors
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalsRepository @Inject constructor(
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
        data.toDomainModel()?.savingsGoals?.let { goals ->
            goals.map { it.lastRefresh = Date() }
            goalDao.saveGoals(goals.mapNotNull { it.toEntity() })
        }
        loadFromDb()
    }

    private fun onLoadFromApiError(error: Throwable) {
        error.printStackTrace()
        listener.onGoalsError(error)
    }

    private fun loadFromDb() {
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
        val savingsGoals = SavingsGoals(goals.mapNotNull { it.toDomainModel() })
        listener.onGoalsSuccess(savingsGoals)
    }

    private fun onLoadFromDbError(error: Throwable) {
        listener.onGoalsError(error)
    }

    interface Listener {
        fun onGoalsSuccess(savingsGoals: SavingsGoals)
        fun onGoalsError(throwable: Throwable)
    }

}
