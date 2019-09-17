package br.com.mygoals.base.repository.api.models

import com.squareup.moshi.Json

data class GoalResponse(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "goalImageURL")
    val goalImageURL: String,
    @field:Json(name = "currentBalance")
    val currentBalance: Float,
    @field:Json(name = "targetAmount")
    val targetAmount: Float?
)
