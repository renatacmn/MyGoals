package br.com.mygoals.goals.list

import androidx.recyclerview.widget.DiffUtil
import br.com.mygoals.R
import br.com.mygoals.base.api.models.Goal
import br.com.mygoals.base.view.DataBindingAdapter

class GoalsListAdapter : DataBindingAdapter<Goal>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Goal>() {
        override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int) = R.layout.list_item_goal

}
