package br.com.mygoals.base.repository.dao.mappers

import br.com.mygoals.base.repository.dao.models.FeedItemEntity
import br.com.mygoals.base.repository.models.FeedItem
import java.util.Date

fun FeedItemEntity?.toDomainModel(): FeedItem? {
    if (this == null) return null
    return FeedItem(
        id,
        timestamp,
        message,
        amount,
        savingsRuleId
    )
}

fun FeedItem?.toEntity(lastRefresh: Date?, goalId: Int?): FeedItemEntity? {
    if (this == null) return null
    return FeedItemEntity(
        id,
        timestamp,
        message,
        amount,
        savingsRuleId,
        lastRefresh,
        goalId
    )
}

fun List<FeedItemEntity>?.toDomainModel(): List<FeedItem> {
    if (this == null) return emptyList()
    return mapNotNull { it.toDomainModel() }
}

fun List<FeedItem>?.toEntity(lastRefresh: Date?, goalId: Int?): List<FeedItemEntity> {
    if (this == null) return emptyList()
    return mapNotNull { it.toEntity(lastRefresh, goalId) }
}
