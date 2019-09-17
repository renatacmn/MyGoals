package br.com.mygoals.base.repository.api.models

import com.squareup.moshi.Json

data class SavingsGoalsApiModel(
    @field:Json(name = "savingsGoals")
    val savingsGoals: List<GoalApiModel>?
)
