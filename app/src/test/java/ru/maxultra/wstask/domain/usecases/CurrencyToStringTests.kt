package ru.maxultra.wstask.domain.usecases

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import ru.maxultra.wstask.domain.entities.currency.Currency
import ru.maxultra.wstask.domain.entities.currency.EnumCurrency

class CurrencyToStringTests {

    @Test
    fun currencyToStringSymbolTest() {
        val currency = Mockito.mock(Currency::class.java)
        assertEquals(currency.name, CurrencyToStringUseCase.execute(currency))
    }

    @Test
    fun enumCurrencyToStringSymbolTest() {
        for (currency in EnumCurrency.values()) {
            assertEquals(currency.name, CurrencyToStringUseCase.execute(currency))
        }
    }
}
