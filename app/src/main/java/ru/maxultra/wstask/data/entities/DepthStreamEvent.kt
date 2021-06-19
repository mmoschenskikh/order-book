package ru.maxultra.wstask.data.entities

import com.squareup.moshi.Json

data class DepthStreamEvent(
    @Json(name = "e") val eventType: String,
    @Json(name = "E") val eventTime: Long,
    @Json(name = "s") val symbol: String,
    @Json(name = "U") val firstUpdateId: Long,
    @Json(name = "u") val finalUpdateId: Long,
    @Json(name = "b") val bidsToUpdate: List<List<String>>,
    @Json(name = "a") val asksToUpdate: List<List<String>>,
)
