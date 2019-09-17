package br.com.mygoals.base.repository.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.mygoals.base.repository.dao.models.FeedItemEntity
import br.com.mygoals.base.repository.dao.models.GoalEntity
import br.com.mygoals.base.repository.dao.models.RuleEntity
import br.com.mygoals.base.repository.dao.util.CustomDateTypeConverter

@Database(entities = [GoalEntity::class, RuleEntity::class, FeedItemEntity::class], version = 1)
@TypeConverters(CustomDateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun ruleDao(): RuleDao
    abstract fun feedItemDao(): FeedItemDao
}
