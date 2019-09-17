package br.com.mygoals.base.repository.dao.mappers

import br.com.mygoals.base.repository.dao.models.GoalEntity
import br.com.mygoals.base.repository.models.Goal
import java.util.Date

fun GoalEntity?.toDomainModel(): Goal? {
    if (this == null) return null
    return Goal(
        id,
        name,
        goalImageURL,
        currentBalance,
        targetAmount
    )
}

fun Goal?.toEntity(lastRefresh: Date?): GoalEntity? {
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

fun List<GoalEntity>?.toDomainModel(): List<Goal> {
    if (this == null) return emptyList()
    return mapNotNull { it.toDomainModel() }
}

fun List<Goal>?.toEntity(lastRefresh: Date?): List<GoalEntity> {
    if (this == null) return emptyList()
    return mapNotNull { it.toEntity(lastRefresh) }
}
