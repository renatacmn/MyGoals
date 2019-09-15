package br.com.mygoals.goals.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mygoals.base.api.MyGoalsRepository
import javax.inject.Inject

class GoalDetailsViewModelFactory @Inject constructor(
    private val repository: MyGoalsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GoalDetailsViewModel(repository) as T
    }
}

class GoalDetailsViewModel(
    private val repository: MyGoalsRepository
) : ViewModel() {


}
