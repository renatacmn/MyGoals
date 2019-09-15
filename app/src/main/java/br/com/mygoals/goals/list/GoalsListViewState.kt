package br.com.mygoals.goals.list

import androidx.databinding.BaseObservable
import br.com.mygoals.base.api.models.Goal

data class GoalsListViewState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var isError: Boolean = false,
    var goalsList: List<Goal> = emptyList(),
    var errorMessage: String? = null
) : BaseObservable() {
    init {
        notifyChange()
    }
}
