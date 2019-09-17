package br.com.mygoals.base.repository.dao.mappers

import br.com.mygoals.base.repository.dao.models.FeedItemEntity
import br.com.mygoals.base.repository.models.FeedItem

fun FeedItemEntity?.toDomainModel(): FeedItem? {
    if (this == null) return null
    return FeedItem(
        id,
        timestamp,
        message,
        amount,
        savingsRuleId,
        lastRefresh
    )
}

fun FeedItem?.toEntity(): FeedItemEntity? {
    if (this == null) return null
    return FeedItemEntity(
        id,
        timestamp,
        message,
        amount,
        savingsRuleId,
        lastRefresh
    )
}
