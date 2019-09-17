package br.com.mygoals.base.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.mygoals.base.repository.dao.models.RuleEntity
import io.reactivex.Single
import java.util.Date

@Dao
interface RuleDao {

    @Query("SELECT * FROM goal_entity")
    fun loadRules(): Single<List<RuleEntity>>

    @Insert(onConflict = REPLACE)
    fun saveRules(rules: List<RuleEntity>)

    @Query("SELECT * FROM rule_entity WHERE lastRefresh > :lastRefreshMax LIMIT 1")
    fun hasRules(lastRefreshMax: Date): Single<RuleEntity>

}
