package br.com.mygoals.base.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.mygoals.base.repository.dao.models.FeedItemEntity
import io.reactivex.Single
import java.util.Date

@Dao
interface FeedItemDao {

    @Query("SELECT * FROM feed_item_entity WHERE goalId = :goalId")
    fun loadFeedItems(goalId: Int): Single<List<FeedItemEntity>>

    @Insert(onConflict = REPLACE)
    fun saveFeedItems(items: List<FeedItemEntity>)

    @Query("SELECT * FROM feed_item_entity WHERE goalId = :goalId AND lastRefresh > :lastRefreshMax LIMIT 1")
    fun hasFeedItems(goalId: Int, lastRefreshMax: Date): Single<FeedItemEntity>

}
