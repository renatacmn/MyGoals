package br.com.mygoals.base.repository.api.mappers

import br.com.mygoals.base.repository.api.models.RuleApiModel
import br.com.mygoals.base.repository.api.models.SavingsRulesApiModel
import br.com.mygoals.base.repository.models.Rule
import br.com.mygoals.base.repository.models.SavingsRules

fun SavingsRulesApiModel?.toDomainModel(): SavingsRules? {
    if (this == null) return null
    return SavingsRules(
        savingsRules?.mapNotNull { it.toDomainModel() }
    )
}

fun RuleApiModel?.toDomainModel(): Rule? {
    if (this == null) return null
    return Rule(
        id,
        type
    )
}
