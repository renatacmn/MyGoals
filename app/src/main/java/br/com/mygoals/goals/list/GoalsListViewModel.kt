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

    var viewState = GoalsListViewState()

    fun getSavingsGoals() {
        add(repository.getSavingsGoals()
            .subscribeOn(executors.networkIO())
            .doOnSubscribe { updateToLoadingViewState() }
            .doOnSuccess { data -> updateToSuccessViewState(data) }
            .doOnError { error -> updateToErrorViewState(error) }
            .observeOn(executors.mainThread())
            .subscribe(this::onSuccess) { e -> onError(e) })
    }

    private fun onSuccess(savingsGoals: SavingsGoals) {

    }

    private fun onError(error: Throwable) {
        updateToErrorViewState(error)
    }

    private fun updateToLoadingViewState() {
        viewState.isLoading.set(true)
        viewState.isSuccess.set(false)
        viewState.isError.set(false)
        viewState.goalsList.set(emptyList())
        viewState.errorMessage.set("")
    }

    private fun updateToSuccessViewState(data: SavingsGoals) {
        viewState.isLoading.set(false)
        viewState.isSuccess.set(true)
        viewState.isError.set(false)
        viewState.goalsList.set(data.savingsGoals ?: emptyList())
        viewState.errorMessage.set("")
    }

    private fun updateToErrorViewState(error: Throwable) {
        viewState.isLoading.set(false)
        viewState.isSuccess.set(false)
        viewState.isError.set(true)
        viewState.goalsList.set(emptyList())
        viewState.errorMessage.set(error.message)
    }

}
