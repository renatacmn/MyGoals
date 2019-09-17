package br.com.mygoals.base.repository.api.models

import com.squareup.moshi.Json
import java.util.Date

data class FeedItemApiModel(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "timestamp")
    val timestamp: Date,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "amount")
    val amount: Float,
    @field:Json(name = "savingsRuleId")
    val savingsRuleId: Int
)
