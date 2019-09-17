package br.com.mygoals.base.repository.dao.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "goal_entity")
data class GoalEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val goalImageURL: String,
    val currentBalance: Float,
    val targetAmount: Float?,
    var lastRefresh: Date?
)
