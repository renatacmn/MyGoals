package br.com.mygoals.base.api.models

import com.squareup.moshi.Json

data class FeedItem(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "type")
    val type: String?,
    @field:Json(name = "timestamp")
    val timestamp: String?,
    @field:Json(name = "message")
    val message: String?,
    @field:Json(name = "amount")
    val amount: Int?,
    @field:Json(name = "userId")
    val userId: String?,
    @field:Json(name = "savingsRuleId")
    val savingsRuleId: Int?
)
