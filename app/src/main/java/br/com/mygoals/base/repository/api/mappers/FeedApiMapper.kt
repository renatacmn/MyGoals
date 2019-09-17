package br.com.mygoals.base.repository.api.mappers

import br.com.mygoals.base.repository.api.models.FeedApiModel
import br.com.mygoals.base.repository.api.models.FeedItemApiModel
import br.com.mygoals.base.repository.models.Feed
import br.com.mygoals.base.repository.models.FeedItem

fun FeedApiModel?.toDomainModel(): Feed? {
    if (this == null) return null
    return Feed(
        feed?.mapNotNull { it.toDomainModel() }
    )
}

fun FeedItemApiModel?.toDomainModel(): FeedItem? {
    if (this == null) return null
    return FeedItem(
        id,
        timestamp,
        message,
        amount,
        savingsRuleId
    )
}
