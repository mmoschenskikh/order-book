package ru.maxultra.wstask.domain.entities.currencypair

import ru.maxultra.wstask.domain.entities.currency.Currency

/*
 Same reason to make CurrencyPair abstract as for Currency interface
 (fetching all the pairs from the API).
 Here I've chosen interface just to be consistent.
 */
interface CurrencyPair {
    val baseCurrency: Currency
    val quoteCurrency: Currency
}
