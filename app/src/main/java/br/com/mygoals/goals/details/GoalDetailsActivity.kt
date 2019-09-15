package br.com.mygoals.goals.details

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import br.com.mygoals.R
import br.com.mygoals.base.BaseActivity
import javax.inject.Inject

class GoalDetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: GoalDetailsViewModelFactory

    private val viewModel: GoalDetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(GoalDetailsViewModel::class.java)
    }

    // Lifecycle methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_details)
    }

}
