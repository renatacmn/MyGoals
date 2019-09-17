package br.com.mygoals.base.repository.models

import java.util.Date

data class Rule(
    val id: Int?,
    val type: String?,
    var lastRefresh: Date?
)
