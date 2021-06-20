package ru.maxultra.wstask.domain.entities

data class Order(
    val baseAmount: Double,
    val quotePrice: Double
) {
    val total = baseAmount * quotePrice
}
