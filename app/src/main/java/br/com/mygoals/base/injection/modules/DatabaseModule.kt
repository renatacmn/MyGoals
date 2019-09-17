package br.com.mygoals.base.injection.modules

import android.content.Context
import androidx.room.Room
import br.com.mygoals.base.repository.dao.AppDatabase
import br.com.mygoals.base.repository.dao.FeedItemDao
import br.com.mygoals.base.repository.dao.GoalDao
import br.com.mygoals.base.repository.dao.RuleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        applicationContext: Context
    ): AppDatabase = Room
        .databaseBuilder(applicationContext, AppDatabase::class.java, "my-goals-db")
        .build()

    @Provides
    @Singleton
    fun provideGoalDao(database: AppDatabase): GoalDao {
        return database.goalDao()
    }

    @Provides
    @Singleton
    fun provideRuleDao(database: AppDatabase): RuleDao {
        return database.ruleDao()
    }

    @Provides
    @Singleton
    fun provideFeedItemDao(database: AppDatabase): FeedItemDao {
        return database.feedItemDao()
    }

}
