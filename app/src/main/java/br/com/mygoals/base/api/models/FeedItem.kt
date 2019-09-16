package br.com.mygoals.base.api.models

import com.squareup.moshi.Json
import java.util.*

data class FeedItem(
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
) {

    fun getRule(): Rule {
        return Rule(savingsRuleId, null)
    }

}
