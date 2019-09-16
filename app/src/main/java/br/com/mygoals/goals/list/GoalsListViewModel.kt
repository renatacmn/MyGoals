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
            .observeOn(executors.mainThread())
            .subscribe(this::updateToSuccessViewState) { error -> updateToErrorViewState(error) })
    }

    private fun updateToLoadingViewState() {
        viewState.apply {
            isLoading.set(true)
            isSuccess.set(false)
            isError.set(false)
            goalsList.set(emptyList())
            errorThrowable.set(Exception())
        }
    }

    private fun updateToSuccessViewState(data: SavingsGoals) {
        viewState.apply {
            isLoading.set(false)
            isSuccess.set(true)
            isError.set(false)
            goalsList.set(data.savingsGoals ?: emptyList())
            errorThrowable.set(Exception())
        }
    }

    private fun updateToErrorViewState(error: Throwable) {
        viewState.apply {
            isLoading.set(false)
            isSuccess.set(false)
            isError.set(true)
            goalsList.set(emptyList())
            errorThrowable.set(error)
        }
    }

}
