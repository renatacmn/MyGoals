package br.com.mygoals.goals.details

import androidx.recyclerview.widget.DiffUtil
import br.com.mygoals.R
import br.com.mygoals.base.api.models.FeedItem
import br.com.mygoals.base.view.DataBindingAdapter

class GoalDetailsFeedAdapter :
    DataBindingAdapter<FeedItem>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<FeedItem>() {
        override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int) = R.layout.list_item_feed

}
