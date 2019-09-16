package br.com.mygoals.goals.list

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import br.com.mygoals.R
import br.com.mygoals.base.BaseActivity
import br.com.mygoals.base.api.models.Goal
import br.com.mygoals.databinding.ActivityGoalListBinding
import br.com.mygoals.goals.details.GoalDetailsActivity
import javax.inject.Inject

class GoalsListActivity : BaseActivity(), GoalsListAdapter.Listener {

    @Inject
    lateinit var viewModelFactory: GoalsListViewModelFactory

    private val viewModel: GoalsListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GoalsListViewModel::class.java)
    }

    // Lifecycle methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        loadData()
    }

    // GoalsListAdapter.Listener override

    override fun onGoalClicked(goal: Goal) {
        GoalDetailsActivity.start(this, goal)
    }

    // Private methods

    private fun initializeBinding() {
        val listAdapter = GoalsListAdapter(this)
        val binding: ActivityGoalListBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_goal_list)
        binding.apply {
            vm = viewModel
            adapter = listAdapter
        }
    }

    private fun loadData() {
        viewModel.getSavingsGoals()
    }

}
