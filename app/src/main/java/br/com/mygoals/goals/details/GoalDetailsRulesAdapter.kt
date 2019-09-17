package br.com.mygoals.goals.details

import androidx.recyclerview.widget.DiffUtil
import br.com.mygoals.R
import br.com.mygoals.base.repository.models.Rule
import br.com.mygoals.base.view.DataBindingAdapter

class GoalDetailsRulesAdapter :
    DataBindingAdapter<Rule>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Rule>() {
        override fun areItemsTheSame(oldItem: Rule, newItem: Rule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Rule, newItem: Rule): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int) = R.layout.list_item_rule

}
