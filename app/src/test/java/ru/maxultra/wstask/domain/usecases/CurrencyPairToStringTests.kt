package ru.maxultra.wstask.domain.usecases

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import ru.maxultra.wstask.domain.entities.currency.Currency
import ru.maxultra.wstask.domain.entities.currency.EnumCurrency
import ru.maxultra.wstask.domain.entities.currencypair.SimpleCurrencyPair

class CurrencyPairToStringTests {

    @Test
    fun simpleCurrencyPairToStringSymbolTest() {
        val base = Mockito.mock(Currency::class.java)
        val quote = Mockito.mock(Currency::class.java)
        val pair = SimpleCurrencyPair(base, quote)
        val expectedString = base.name + quote.name
        assertEquals(expectedString, CurrencyPairToStringUseCase.execute(pair))
    }

    @Test
    fun simpleCurrencyPairWithEnumCurrencyToStringSymbolTest() {
        for (base in EnumCurrency.values()) {
            for (quote in EnumCurrency.values()) {
                val pair = SimpleCurrencyPair(base, quote)
                val expectedString = base.name + quote.name
                assertEquals(expectedString, CurrencyPairToStringUseCase.execute(pair))
            }
        }
    }
}
