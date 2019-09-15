package br.com.mygoals.goals.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mygoals.base.AutoDisposableViewModel
import br.com.mygoals.base.api.MyGoalsRepository
import br.com.mygoals.base.api.models.SavingsGoals
import br.com.mygoals.util.executors.Executors
import io.reactivex.subjects.BehaviorSubject
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

    private val _state: BehaviorSubject<GoalsListViewState> = BehaviorSubject.create()
    var state = GoalsListViewState()

    fun getSavingsGoals() {
        add(repository.getSavingsGoals()
            .subscribeOn(executors.networkIO())
            .doOnSubscribe { state = getLoadingViewState() }
            .doOnSuccess { data -> state = getSuccessViewState(data) }
            .doOnError { error -> state = getErrorViewState(error) }
            .observeOn(executors.mainThread())
            .subscribe(this::onSuccess) { e -> onError(e) })
    }

    private fun onSuccess(savingsGoals: SavingsGoals) {

    }

    private fun onError(error: Throwable) {
        _state.onNext(getErrorViewState(error))
    }

    private fun getLoadingViewState(): GoalsListViewState {
        return GoalsListViewState(
            isLoading = true,
            isSuccess = false,
            isError = false,
            goalsList = emptyList(),
            errorMessage = null
        )
    }

    private fun getSuccessViewState(data: SavingsGoals): GoalsListViewState {
        return GoalsListViewState(
            isLoading = false,
            isSuccess = true,
            isError = false,
            goalsList = data.savingsGoals ?: emptyList(),
            errorMessage = null
        )
    }

    private fun getErrorViewState(error: Throwable): GoalsListViewState {
        return GoalsListViewState(
            isLoading = false,
            isSuccess = false,
            isError = true,
            goalsList = emptyList(),
            errorMessage = error.localizedMessage
        )
    }

}
