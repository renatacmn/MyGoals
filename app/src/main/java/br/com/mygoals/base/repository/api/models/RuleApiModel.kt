package br.com.mygoals.base.repository.api.models

import com.squareup.moshi.Json

data class RuleApiModel(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "type")
    val type: String?
)
