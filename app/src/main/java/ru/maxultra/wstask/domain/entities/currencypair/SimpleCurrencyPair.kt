package ru.maxultra.wstask.domain.entities.currencypair

import ru.maxultra.wstask.domain.entities.currency.Currency

data class SimpleCurrencyPair(
    override val baseCurrency: Currency,
    override val quoteCurrency: Currency
) : CurrencyPair
