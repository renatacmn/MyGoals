package br.com.mygoals.testUtil

import br.com.mygoals.base.repository.api.mappers.toDomainModel
import br.com.mygoals.base.repository.api.models.FeedApiModel
import br.com.mygoals.base.repository.api.models.FeedItemApiModel
import br.com.mygoals.base.repository.dao.mappers.toEntity
import br.com.mygoals.base.repository.dao.models.FeedItemEntity
import br.com.mygoals.base.repository.models.FeedItem
import java.util.Date

class FeedTestDataUtil {

    fun goalId() = 1

    fun feedApiModel(): FeedApiModel {
        return FeedApiModel(feedItemApiModelList())
    }

    fun feedItemEntity(id: Int = 1): FeedItemEntity? {
        return feedItem(id).toEntity(null, id)
    }

    fun feedItem(id: Int): FeedItem? {
        return feedItemApiModel(id).toDomainModel()
    }

    // Private methods

    private fun feedItemApiModelList(): List<FeedItemApiModel> {
        return listOf(feedItemApiModel(1), feedItemApiModel(2), feedItemApiModel(3))
    }

    private fun feedItemApiModel(id: Int): FeedItemApiModel {
        return FeedItemApiModel(
            id.toString(),
            Date(),
            "FeedItem $id",
            id.toFloat(),
            id
        )
    }

}
