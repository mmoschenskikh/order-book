package ru.maxultra.wstask.domain.usecases

import ru.maxultra.wstask.domain.entities.currency.Currency

object CurrencyToStringUseCase {

    /**
     * Converts a currency pair to a string like "BTC".
     */
    fun execute(currency: Currency) = currency.name
}
