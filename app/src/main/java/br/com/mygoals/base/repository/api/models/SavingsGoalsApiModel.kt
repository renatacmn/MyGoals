package br.com.mygoals.base.repository.models

import com.squareup.moshi.Json

data class SavingsGoals(
    @field:Json(name = "savingsGoals")
    val savingsGoals: List<Goal>?
)
