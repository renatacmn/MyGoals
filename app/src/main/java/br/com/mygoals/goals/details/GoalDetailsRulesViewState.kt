package br.com.mygoals.goals.details

import androidx.databinding.ObservableField
import br.com.mygoals.base.BaseViewState
import br.com.mygoals.base.repository.models.Rule

data class GoalDetailsRulesViewState(
    var rulesList: ObservableField<List<Rule>> = ObservableField(emptyList())
) : BaseViewState() {

    override fun onLoading() {
        super.onLoading()
        rulesList.set(emptyList())
    }

    override fun onError(error: Throwable) {
        super.onError(error)
        rulesList.set(emptyList())
    }

    fun onSuccess(list: List<Rule>) {
        super.onSuccess()
        rulesList.set(list)
    }

}
