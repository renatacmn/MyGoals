package br.com.mygoals.base.repository

import br.com.mygoals.base.BaseRepository
import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.GoalDao
import br.com.mygoals.base.repository.dao.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.models.SavingsGoals
import io.reactivex.Single
import java.util.Date
import javax.inject.Inject

class GoalsRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val goalDao: GoalDao
) : BaseRepository() {

    fun getSavingsGoals() = loadFromDbRefreshingIfNecessary()

    private fun loadFromDbRefreshingIfNecessary(): Single<SavingsGoals> {
        return goalDao.hasGoals(getMaxRefreshTime())
            .flatMap {
                // Exists on DB
                goalDao.loadGoals()
                    .map { SavingsGoals(it.toDomainModel()) }
            }
            .onErrorResumeNext {
                // Doesn't exist on DB or couldn't be loaded
                api.getSavingsGoals()
                    .map { it.toDomainModel() }
                    .flatMap { savingsGoals ->
                        savingsGoals.savingsGoals?.let { goalsList ->
                            goalDao.saveGoals(goalsList.toEntity(Date()))
                        }
                        goalDao.loadGoals()
                            .map { SavingsGoals(it.toDomainModel()) }
                    }
            }
    }

}
