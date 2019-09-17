package br.com.mygoals.base.repository.api.mappers

import br.com.mygoals.base.repository.api.models.GoalApiModel
import br.com.mygoals.base.repository.models.Goal


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
