package br.com.mygoals.base.repository.dao.mappers

import br.com.mygoals.base.repository.dao.models.RuleEntity
import br.com.mygoals.base.repository.models.Rule
import java.util.Date

fun RuleEntity?.toDomainModel(): Rule? {
    if (this == null) return null
    return Rule(
        id,
        type
    )
}

fun Rule?.toEntity(lastRefresh: Date?): RuleEntity? {
    if (this == null) return null
    return RuleEntity(
        id,
        type,
        lastRefresh
    )
}

fun List<RuleEntity>?.toDomainModel(): List<Rule> {
    if (this == null) return emptyList()
    return mapNotNull { it.toDomainModel() }
}

fun List<Rule>?.toEntity(lastRefresh: Date?): List<RuleEntity> {
    if (this == null) return emptyList()
    return mapNotNull { it.toEntity(lastRefresh) }
}
