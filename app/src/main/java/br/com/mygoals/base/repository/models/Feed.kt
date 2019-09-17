package br.com.mygoals.base.repository.models

import com.squareup.moshi.Json

data class Feed(
    @field:Json(name = "feed")
    val feed: List<FeedItem>?
)
