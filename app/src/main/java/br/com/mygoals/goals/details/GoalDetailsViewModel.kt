package br.com.mygoals.goals.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mygoals.base.AutoDisposableViewModel
import br.com.mygoals.base.api.MyGoalsRepository
import br.com.mygoals.base.api.models.Feed
import br.com.mygoals.base.api.models.Goal
import br.com.mygoals.base.api.models.SavingsRules
import br.com.mygoals.util.executors.Executors
import javax.inject.Inject

class GoalDetailsViewModelFactory @Inject constructor(
    private val repository: MyGoalsRepository,
    private val executors: Executors
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GoalDetailsViewModel(repository, executors) as T
    }
}

class GoalDetailsViewModel(
    private val repository: MyGoalsRepository,
    private val executors: Executors
) : AutoDisposableViewModel() {

    val rulesViewState = GoalDetailsRulesViewState()
    val feedViewState = GoalDetailsFeedViewState()

    private var goal: Goal? = null

    fun init(goal: Goal?) {
        this.goal = goal
    }

    fun getRules() {
        add(repository.getSavingsRules()
            .subscribeOn(executors.networkIO())
            .doOnSubscribe { rulesViewState.onLoading() }
            .observeOn(executors.mainThread())
            .subscribe(this::onRulesSuccess) { error ->
                rulesViewState.onError(error)
            })
    }

    fun getFeed() {
        goal?.let { goal ->
            add(repository.getFeed(goal.id)
                .subscribeOn(executors.networkIO())
                .doOnSubscribe { feedViewState.onLoading() }
                .observeOn(executors.mainThread())
                .subscribe(this::onFeedSuccess) { error ->
                    feedViewState.onError(error)
                })
        }
    }

    private fun onRulesSuccess(data: SavingsRules) {
        rulesViewState.onSuccess(data.savingsRules ?: emptyList())
    }

    private fun onFeedSuccess(data: Feed) {
        feedViewState.onSuccess(data.feed ?: emptyList())
    }

}
