package br.com.mygoals.base.repository.api.mappers

import br.com.mygoals.base.repository.api.models.GoalApiModel
import br.com.mygoals.base.repository.models.Goal

fun GoalApiModel?.toDomainModel(): Goal? {
    if (this == null) return null
    return Goal(
        id,
        name,
        goalImageURL,
        currentBalance,
        targetAmount,
        null
    )
}

fun Goal?.toApiModel(): GoalApiModel? {
    if (this == null) return null
    return GoalApiModel(
        id,
        name,
        goalImageURL,
        currentBalance,
        targetAmount
    )
}
