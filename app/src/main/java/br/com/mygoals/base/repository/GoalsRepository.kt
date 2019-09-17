package br.com.mygoals.base.repository

import br.com.mygoals.base.BaseRepository2
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

class GoalsRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val goalDao: GoalDao,
    private val repositoryUtil: RepositoryUtil,
    executors: Executors
) : BaseRepository2(executors) {

    private lateinit var listener: Listener

    fun getSavingsGoals(listener: Listener) {
        this.listener = listener
        loadFromDbRefreshingIfNecessary()
    }

    override fun loadFromDbRefreshingIfNecessary() {
        super.loadFromDbRefreshingIfNecessary(
            goalDao.hasGoals(repositoryUtil.getMaxRefreshTime())
        )
    }

    override fun loadFromApi() {
        super.loadFromApi(api.getSavingsGoals())
    }

    override fun <T> onLoadFromApiSuccess(data: T) {
        Timber.d("> Loaded successfully from API. Will save on DB")
        val savingsGoalsApiModel = data as SavingsGoalsApiModel
        savingsGoalsApiModel.toDomainModel()?.savingsGoals?.let { goals ->
            goals.map { it.lastRefresh = Date() }
            goalDao.saveGoals(goals.mapNotNull { it.toEntity() })
        }
        loadFromDb()
    }

    override fun loadFromDb() {
        super.loadFromDb(goalDao.loadGoals())
    }

    override fun <T> onLoadFromDbSuccess(data: T) {
        Timber.d("> Loaded successfully from DB. Will send to view")
        val goals = data as List<GoalEntity>
        val savingsGoals = SavingsGoals(goals.mapNotNull { it.toDomainModel() })
        listener.onGoalsSuccess(savingsGoals)
    }

    override fun emitError(error: Throwable) {
        listener.onGoalsError(error)
    }

    interface Listener {
        fun onGoalsSuccess(savingsGoals: SavingsGoals)
        fun onGoalsError(throwable: Throwable)
    }

}
