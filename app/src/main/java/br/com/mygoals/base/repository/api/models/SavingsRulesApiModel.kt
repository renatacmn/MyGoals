package br.com.mygoals.base.repository.api.models

import com.squareup.moshi.Json

data class SavingsRulesApiModel(
    @field:Json(name = "savingsRules")
    val savingsRules: List<RuleApiModel>?
)
