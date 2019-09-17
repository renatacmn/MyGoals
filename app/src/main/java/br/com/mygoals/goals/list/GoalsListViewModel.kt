package br.com.mygoals.goals.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mygoals.base.AutoDisposableViewModel
import br.com.mygoals.base.repository.GoalsRepository
import br.com.mygoals.base.repository.models.SavingsGoals
import br.com.mygoals.util.executors.Executors
import javax.inject.Inject

class GoalsListViewModelFactory @Inject constructor(
    private val goalsRepository: GoalsRepository,
    private val executors: Executors
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GoalsListViewModel(goalsRepository, executors) as T
    }
}

class GoalsListViewModel(
    private val goalsRepository: GoalsRepository,
    private val executors: Executors
) : AutoDisposableViewModel() {

    val viewState = GoalsListViewState()

    fun getSavingsGoals() {
        add(goalsRepository.getSavingsGoals()
            .subscribeOn(executors.diskIO())
            .doOnSubscribe { viewState.onLoading() }
            .observeOn(executors.mainThread())
            .subscribe(this::onSuccess, this::onError)
        )
    }

    private fun onSuccess(data: SavingsGoals) {
        viewState.onSuccess(data.savingsGoals ?: emptyList())
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
        viewState.onError(error)
    }

}
