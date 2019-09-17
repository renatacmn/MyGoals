package br.com.mygoals.base.repository.dao.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rule_entity")
data class RuleEntity(
    @PrimaryKey
    val id: Int?,
    val type: String?
)
