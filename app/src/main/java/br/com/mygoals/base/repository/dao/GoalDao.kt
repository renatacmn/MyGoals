package br.com.mygoals.base.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.mygoals.base.repository.dao.models.GoalEntity
import io.reactivex.Single
import java.util.Date

@Dao
interface GoalDao {

    @Query("SELECT * FROM goal_entity")
    fun loadGoals(): Single<List<GoalEntity>>

    @Insert(onConflict = REPLACE)
    fun saveGoals(goals: List<GoalEntity>)

    @Query("SELECT * FROM goal_entity WHERE lastRefresh > :lastRefreshMax LIMIT 1")
    fun hasGoals(lastRefreshMax: Date): Single<GoalEntity>

}
