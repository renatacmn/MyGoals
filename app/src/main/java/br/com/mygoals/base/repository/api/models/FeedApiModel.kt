package br.com.mygoals.base.repository.api.models

import com.squareup.moshi.Json

data class FeedApiModel(
    @field:Json(name = "feed")
    val feed: List<FeedItemApiModel>?
)
