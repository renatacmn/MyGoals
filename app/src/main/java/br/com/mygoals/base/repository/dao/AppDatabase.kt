package br.com.mygoals.base.repository.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.mygoals.base.repository.dao.models.GoalEntity
import br.com.mygoals.base.repository.models.FeedItem
import br.com.mygoals.base.repository.models.Rule

@Database(entities = [GoalEntity::class, Rule::class, FeedItem::class], version = 1)
@TypeConverters(CustomDateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
}
