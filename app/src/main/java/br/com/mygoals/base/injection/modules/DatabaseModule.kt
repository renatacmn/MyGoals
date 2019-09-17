package br.com.mygoals.base.injection.modules

import android.content.Context
import androidx.room.Room
import br.com.mygoals.base.repository.dao.AppDatabase
import br.com.mygoals.base.repository.dao.GoalDao
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
    fun provideUserDao(database: AppDatabase): GoalDao {
        return database.goalDao()
    }

}
