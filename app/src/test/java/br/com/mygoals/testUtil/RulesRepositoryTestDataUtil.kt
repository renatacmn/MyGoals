package br.com.mygoals.testUtil

import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.api.models.RuleApiModel
import br.com.mygoals.base.repository.api.models.SavingsRulesApiModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.dao.models.RuleEntity
import br.com.mygoals.base.repository.models.Rule

class RulesRepositoryTestDataUtil {

    fun savingsRulesApiModel(): SavingsRulesApiModel {
        return SavingsRulesApiModel(ruleApiModelList())
    }

    fun ruleEntity(id: Int = 1): RuleEntity? {
        return rule(id).toEntity(null)
    }

    // Private methods

    private fun ruleApiModelList(): List<RuleApiModel> {
        return listOf(ruleApiModel(1), ruleApiModel(2), ruleApiModel(3))
    }

    private fun ruleApiModel(id: Int): RuleApiModel {
        return RuleApiModel(
            id,
            "Rule $id"
        )
    }

    private fun rule(id: Int): Rule? {
        return ruleApiModel(id).toDomainModel()
    }

}
