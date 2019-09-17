package br.com.mygoals.base.api.models

import com.squareup.moshi.Json

data class SavingsRules(
    @field:Json(name = "savingsRules")
    val savingsRules: List<Rule>?
)
