package br.com.mygoals.base.api.models

import com.squareup.moshi.Json

data class SavingsGoals(
    @field:Json(name = "savingsGoals")
    val savingsGoals: List<Goal>?
)
