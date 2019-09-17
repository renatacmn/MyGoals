package br.com.mygoals.base.repository.models

import java.util.Date

data class FeedItem(
    val id: String,
    val timestamp: Date,
    val message: String,
    val amount: Float,
    val savingsRuleId: Int
) {

    fun getRule(): Rule {
        return Rule(savingsRuleId, null)
    }

}
