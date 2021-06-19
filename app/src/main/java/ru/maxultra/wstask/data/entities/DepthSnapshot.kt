package ru.maxultra.wstask.data.entities

data class DepthSnapshot(
    val lastUpdateId: Long,
    val bids: List<List<String>>,
    val asks: List<List<String>>
)
