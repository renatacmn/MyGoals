package br.com.mygoals.base.repository.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Rule(
    @PrimaryKey
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "type")
    val type: String?
)
