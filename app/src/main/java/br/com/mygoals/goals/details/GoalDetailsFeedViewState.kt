package br.com.mygoals.goals.details

import androidx.databinding.ObservableField
import br.com.mygoals.base.BaseViewState
import br.com.mygoals.base.api.models.FeedItem

data class GoalDetailsFeedViewState(
    var feedList: ObservableField<List<FeedItem>> = ObservableField(emptyList())
) : BaseViewState() {

    override fun onLoading() {
        super.onLoading()
        feedList.set(emptyList())
    }

    override fun onError(error: Throwable) {
        super.onError(error)
        feedList.set(emptyList())
    }

    fun onSuccess(list: List<FeedItem>) {
        super.onSuccess()
        feedList.set(list)
    }

}
