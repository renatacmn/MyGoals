package br.com.mygoals.base.api.models

import com.squareup.moshi.Json

data class Rule(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "type")
    val type: String?
)
