package ru.maxultra.wstask.domain.usecases

import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair


object CurrencyPairToStringUseCase {

    /**
     * Converts a currency pair to a string symbol like "BTCUSDT".
     */
    fun execute(currencyPair: CurrencyPair): String {
        val base = CurrencyToStringUseCase.execute(currencyPair.baseCurrency)
        val quote = CurrencyToStringUseCase.execute(currencyPair.quoteCurrency)
        return base + quote
    }
}
