package br.com.mygoals.goals.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mygoals.base.AutoDisposableViewModel
import br.com.mygoals.base.repository.FeedRepository
import br.com.mygoals.base.repository.RulesRepository
import br.com.mygoals.base.repository.models.Feed
import br.com.mygoals.base.repository.models.Goal
import br.com.mygoals.base.repository.models.SavingsRules
import br.com.mygoals.util.executors.Executors
import javax.inject.Inject

class GoalDetailsViewModelFactory @Inject constructor(
    private val rulesRepository: RulesRepository,
    private val feedRepository: FeedRepository,
    private val executors: Executors
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GoalDetailsViewModel(rulesRepository, feedRepository, executors) as T
    }
}

class GoalDetailsViewModel(
    private val rulesRepository: RulesRepository,
    private val feedRepository: FeedRepository,
    private val executors: Executors
) : AutoDisposableViewModel(), FeedRepository.Listener {

    val rulesViewState = GoalDetailsRulesViewState()
    val feedViewState = GoalDetailsFeedViewState()

    private var goal: Goal? = null

    fun init(goal: Goal?) {
        this.goal = goal
    }

    fun getRules() {
        add(rulesRepository.getSavingsRules()
            .subscribeOn(executors.networkIO())
            .doOnSubscribe { rulesViewState.onLoading() }
            .observeOn(executors.mainThread())
            .subscribe(this::onRulesSuccess) { error ->
                rulesViewState.onError(error)
            })
    }

    fun getFeed() {
        goal?.id?.let { goalId ->
            feedViewState.onLoading()
            feedRepository.getFeed(this, goalId)
        }
    }

    // FeedRepository.Listener overrides

    override fun onFeedSuccess(feed: Feed) {
        feedViewState.onSuccess(feed.feed ?: emptyList())
    }

    override fun onFeedError(throwable: Throwable) {
        feedViewState.onError(throwable)
    }

    // RulesRepository.Listener overrides

    private fun onRulesSuccess(data: SavingsRules) {
        rulesViewState.onSuccess(data.savingsRules ?: emptyList())
    }

    // ViewModel overrides

    override fun onCleared() {
        super.onCleared()
        feedRepository.dispose()
    }

}
