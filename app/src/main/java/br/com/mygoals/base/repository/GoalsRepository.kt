package br.com.mygoals.base.repository

import br.com.mygoals.base.BaseRepository
import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.GoalDao
import br.com.mygoals.base.repository.dao.mappers.toDomainModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.models.SavingsGoals
import br.com.mygoals.base.repository.util.RepositoryUtil
import io.reactivex.Single
import java.util.Date
import javax.inject.Inject

class GoalsRepository @Inject constructor(
    private val api: MyGoalsApi,
    private val goalDao: GoalDao,
    private val repositoryUtil: RepositoryUtil
) : BaseRepository() {

    fun getSavingsGoals() = loadFromDbRefreshingIfNecessary()

    // Private methods

    private fun loadFromDbRefreshingIfNecessary(): Single<SavingsGoals> {
        return goalDao.hasGoals(repositoryUtil.getMaxRefreshTime())
            .flatMap {
                // Has goals on DB
                goalDao.loadGoals()
                    .map { goalEntities ->
                        SavingsGoals(goalEntities.mapNotNull { it.toDomainModel() })
                    }
            }
            .onErrorResumeNext {
                // Doesn't have goals on DB or couldn't load
                api.getSavingsGoals()
                    .map { it.toDomainModel() }
                    .flatMap { savingsGoals ->
                        savingsGoals.savingsGoals?.let { goalsList ->
                            goalsList.map { it.lastRefresh = Date() }
                            goalDao.saveGoals(goalsList.toEntity())
                        }
                        goalDao.loadGoals()
                            .map { goalEntities ->
                                SavingsGoals(goalEntities.mapNotNull { it.toDomainModel() })
                            }
                    }
            }
    }

}
