package br.com.mygoals.base.repository.api.mappers

import br.com.mygoals.base.repository.api.models.GoalApiModel
import br.com.mygoals.base.repository.models.Goal

class GoalMapper {

    fun toDomainModel(goalApiModel: GoalApiModel): Goal {
        return Goal(
            id,
            name,
            goalImageURL,
            currentBalance,
            targetAmount,
            null
        )
    }

    fun fromDomainModel(goal: Goal): GoalApiModel {
        return GoalApiModel(
            goal.id,
            goal.name,
            goal.goalImageURL,
            goal.currentBalance,
            goal.targetAmount
        )
    }

}

fun GoalApiModel.toDomainModel(): Goal {
    return Goal(
        id,
        name,
        goalImageURL,
        currentBalance,
        targetAmount,
        null
    )
}

fun Goal.toApiModel(): GoalApiModel {
    return GoalApiModel(
        id,
        name,
        goalImageURL,
        currentBalance,
        targetAmount
    )
}