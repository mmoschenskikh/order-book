package ru.maxultra.wstask.domain.entities

import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair

data class Difference(
    val currencyPair: CurrencyPair,
    val priceToUpdate: Double,
    val diff: Double
)
