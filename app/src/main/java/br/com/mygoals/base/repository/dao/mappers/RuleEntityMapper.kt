package br.com.mygoals.base.repository.dao.mappers

import br.com.mygoals.base.repository.dao.models.RuleEntity
import br.com.mygoals.base.repository.models.Rule

fun RuleEntity?.toDomainModel(): Rule? {
    if (this == null) return null
    return Rule(
        id,
        type
    )
}

fun Rule?.toEntity(): RuleEntity? {
    if (this == null) return null
    return RuleEntity(
        id,
        type
    )
}
