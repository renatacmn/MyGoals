package br.com.mygoals.testUtil

import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.api.models.GoalApiModel
import br.com.mygoals.base.repository.api.models.SavingsGoalsApiModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.dao.models.GoalEntity
import br.com.mygoals.base.repository.models.Goal
import br.com.mygoals.base.repository.models.SavingsGoals

class GoalsTestDataUtil {

    fun savingsGoalsApiModel(): SavingsGoalsApiModel {
        return SavingsGoalsApiModel(goalApiModelList())
    }

    fun savingsGoals(): SavingsGoals? {
        return savingsGoalsApiModel().toDomainModel()
    }

    fun goalEntity(id: Int = 1): GoalEntity? {
        return goal(id).toEntity(null)
    }

    // Private methods

    private fun goalApiModelList(): List<GoalApiModel> {
        return listOf(goalApiModel(1), goalApiModel(2), goalApiModel(3))
    }

    private fun goalApiModel(id: Int): GoalApiModel {
        return GoalApiModel(
            id,
            "Goal $id",
            "",
            id.toFloat(),
            id.toFloat()
        )
    }

    private fun goal(id: Int): Goal? {
        return goalApiModel(id).toDomainModel()
    }

}
