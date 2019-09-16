package br.com.mygoals.goals.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import br.com.mygoals.R
import br.com.mygoals.base.BaseActivity
import br.com.mygoals.base.api.models.Goal
import br.com.mygoals.databinding.ActivityGoalDetailsBinding
import javax.inject.Inject

class GoalDetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: GoalDetailsViewModelFactory

    private val viewModel: GoalDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GoalDetailsViewModel::class.java)
    }

    private var currentGoal: Goal? = null

    // Lifecycle methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtras()
        initializeBinding()
    }

    // Private methods

    private fun getExtras() {
        currentGoal = intent.extras?.getParcelable(PARAM_GOAL)
    }

    private fun initializeBinding() {
        val binding: ActivityGoalDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_goal_details)
        binding.apply {
            goal = currentGoal
        }
    }

    companion object {
        private const val PARAM_GOAL = "param_goal"
        fun start(context: Context, goal: Goal) {
            val intent = Intent(context, GoalDetailsActivity::class.java)
            val extras = Bundle().apply {
                putParcelable(PARAM_GOAL, goal)
            }
            intent.putExtras(extras)
            context.startActivity(intent)
        }
    }

}
