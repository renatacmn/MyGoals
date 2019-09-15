package br.com.mygoals.base.injection.modules

import br.com.mygoals.goals.details.GoalDetailsActivity
import br.com.mygoals.goals.list.GoalsListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindings {

    @ContributesAndroidInjector
    abstract fun contributesGoalsListActivity(): GoalsListActivity

    @ContributesAndroidInjector
    abstract fun contributesGoalDetailsActivity(): GoalDetailsActivity

}
