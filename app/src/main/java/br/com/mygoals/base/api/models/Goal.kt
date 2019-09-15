package br.com.mygoals.base.api.models

import com.squareup.moshi.Json

data class Goal(
    @field:Json(name = "created")
    val created: List<Int>,
    @field:Json(name = "currentBalance")
    val currentBalance: Int,
    @field:Json(name = "goalImageURL")
    val goalImageURL: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "targetAmount")
    val targetAmount: Int?,
    @field:Json(name = "userId")
    val userId: Int
)
