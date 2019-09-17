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
        loadGoalsFromDbRefreshingIfNecessary()
    }

    // Private methods

    private fun loadGoalsFromDbRefreshingIfNecessary() {
        add(
            goalDao.hasGoals(repositoryUtil.getMaxRefreshTime())
                .subscribeOn(executors.diskIO())
                .subscribe(
                    (this::onCheckIfHasGoalsSuccess),
                    (this::onCheckIfHasGoalsError)
                )
        )
    }

    private fun onCheckIfHasGoalsSuccess(goalEntity: GoalEntity?) {
        val hasGoals = goalEntity != null
        if (!hasGoals) {
            loadGoalsFromApi()
        } else {
            loadGoalsFromDb()
        }
    }

    private fun onCheckIfHasGoalsError(error: Throwable) {
        error.printStackTrace()
        loadGoalsFromApi()
    }

    private fun loadGoalsFromApi() {
        add(
            api.getSavingsGoals()
                .subscribeOn(executors.networkIO())
                .observeOn(executors.diskIO())
                .subscribe(
                    (this::onLoadGoalsFromApiSuccess),
                    (this::onLoadGoalsFromApiError)
                )
        )
    }

    private fun onLoadGoalsFromApiSuccess(data: SavingsGoalsApiModel) {
        data.toDomainModel()?.savingsGoals?.let { goals ->
            goals.map { it.lastRefresh = Date() }
            goalDao.saveGoals(goals.mapNotNull { it.toEntity() })
        }
        loadGoalsFromDb()
    }

    private fun onLoadGoalsFromApiError(error: Throwable) {
        error.printStackTrace()
        listener.onError(error)
    }

    private fun loadGoalsFromDb() {
        add(
            goalDao.loadGoals()
                .subscribeOn(executors.diskIO())
                .observeOn(executors.mainThread())
                .subscribe(
                    (this::onLoadGoalsFromDbSuccess),
                    (this::onLoadGoalsFromDbError)
                )
        )
    }

    private fun onLoadGoalsFromDbSuccess(goals: List<GoalEntity>) {
        val savingsGoals = SavingsGoals(goals.mapNotNull { it.toDomainModel() })
        listener.onSuccess(savingsGoals)
    }

    private fun onLoadGoalsFromDbError(error: Throwable) {
        listener.onError(error)
    }

    interface Listener {
        fun onSuccess(savingsGoals: SavingsGoals)
        fun onError(throwable: Throwable)
    }

}
