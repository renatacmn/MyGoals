package br.com.mygoals.base.repository.dao.mappers

import br.com.mygoals.base.repository.dao.models.GoalEntity
import br.com.mygoals.base.repository.models.Goal

fun GoalEntity?.toDomainModel(): Goal? {
    if (this == null) return null
    return Goal(
        id,
        name,
        goalImageURL,
        currentBalance,
        targetAmount,
        lastRefresh
    )
}

fun Goal?.toEntity(): GoalEntity? {
    if (this == null) return null
    return GoalEntity(
        id,
        name,
        goalImageURL,
        currentBalance,
        targetAmount,
        lastRefresh
    )
}
