package br.com.mygoals.goals.list

import androidx.databinding.ObservableField
import br.com.mygoals.base.BaseViewState
import br.com.mygoals.base.repository.models.Goal

data class GoalsListViewState(
    var goalsList: ObservableField<List<Goal>> = ObservableField(emptyList())
) : BaseViewState() {

    override fun onLoading() {
        super.onLoading()
        goalsList.set(emptyList())
    }

    override fun onError(error: Throwable) {
        super.onError(error)
        goalsList.set(emptyList())
    }

    fun onSuccess(list: List<Goal>) {
        super.onSuccess()
        goalsList.set(list)
    }

}
