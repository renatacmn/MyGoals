package br.com.mygoals.base.repository.dao.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "feed_item_entity")
data class FeedItemEntity(
    @PrimaryKey
    val id: String,
    val timestamp: Date,
    val message: String,
    val amount: Float,
    val savingsRuleId: Int,
    val lastRefresh: Date?,
    val goalId: Int?
)
