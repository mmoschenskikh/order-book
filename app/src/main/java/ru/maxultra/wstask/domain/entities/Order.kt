package ru.maxultra.wstask.domain.entities

import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair

data class Order(
    val currencyPair: CurrencyPair,
    val baseAmount: Double,
    val quotePrice: Double
) {
    val total = baseAmount * quotePrice
}
