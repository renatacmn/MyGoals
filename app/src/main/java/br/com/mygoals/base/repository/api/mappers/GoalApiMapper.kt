package br.com.mygoals.base.repository.api.mappers

import br.com.mygoals.base.repository.api.models.GoalApiModel
import br.com.mygoals.base.repository.api.models.SavingsGoalsApiModel
import br.com.mygoals.base.repository.models.Goal
import br.com.mygoals.base.repository.models.SavingsGoals

fun SavingsGoalsApiModel?.toDomainModel(): SavingsGoals? {
    if (this == null) return null
    return SavingsGoals(
        savingsGoals?.mapNotNull { it.toDomainModel() }
    )
}

fun GoalApiModel?.toDomainModel(): Goal? {
    if (this == null) return null
    return Goal(
        id,
        name,
        goalImageURL,
        currentBalance,
        targetAmount
    )
}
