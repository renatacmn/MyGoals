package br.com.mygoals.base.repository.models

import com.squareup.moshi.Json

data class SavingsRules(
    @field:Json(name = "savingsRules")
    val savingsRules: List<Rule>?
)
