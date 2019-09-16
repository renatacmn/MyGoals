package br.com.mygoals.goals.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mygoals.base.AutoDisposableViewModel
import br.com.mygoals.base.api.MyGoalsRepository
import br.com.mygoals.base.api.models.SavingsGoals
import br.com.mygoals.util.executors.Executors
import javax.inject.Inject

class GoalsListViewModelFactory @Inject constructor(
    private val repository: MyGoalsRepository,
    private val executors: Executors
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GoalsListViewModel(repository, executors) as T
    }
}

class GoalsListViewModel(
    private val repository: MyGoalsRepository,
    private val executors: Executors
) : AutoDisposableViewModel() {

    val viewState = GoalsListViewState()

    fun getSavingsGoals() {
        add(repository.getSavingsGoals()
            .subscribeOn(executors.networkIO())
            .doOnSubscribe { viewState.onLoading() }
            .observeOn(executors.mainThread())
            .subscribe(this::onSuccess) { error -> viewState.onError(error) })
    }

    private fun onSuccess(data: SavingsGoals) {
        viewState.onSuccess(data.savingsGoals ?: emptyList())
    }

}
