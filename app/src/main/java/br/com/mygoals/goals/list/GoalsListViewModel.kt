package br.com.mygoals.goals.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mygoals.base.repository.GoalsRepository
import br.com.mygoals.base.repository.models.SavingsGoals
import javax.inject.Inject

class GoalsListViewModelFactory @Inject constructor(
    private val goalsRepository: GoalsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GoalsListViewModel(goalsRepository) as T
    }
}

class GoalsListViewModel(
    private val goalsRepository: GoalsRepository
) : ViewModel(), GoalsRepository.Listener {

    val viewState = GoalsListViewState()

    fun getSavingsGoals() {
        viewState.onLoading()
        goalsRepository.getSavingsGoals(this)
    }

    // GoalsRepository overrides

    override fun onGoalsSuccess(savingsGoals: SavingsGoals) {
        viewState.onSuccess(savingsGoals.savingsGoals ?: emptyList())
    }

    override fun onGoalsError(throwable: Throwable) {
        viewState.onError(throwable)
    }

    // ViewModel overrides

    override fun onCleared() {
        super.onCleared()
        goalsRepository.dispose()
    }

}
