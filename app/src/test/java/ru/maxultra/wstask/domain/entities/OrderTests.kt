package ru.maxultra.wstask.domain.entities

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair

class OrderTests {

    private lateinit var currencyPair: CurrencyPair

    @Before
    fun initMocks() {
        currencyPair = Mockito.mock(CurrencyPair::class.java)
    }

    @Test
    fun orderTotalTest() {
        var order = Order(currencyPair, 2.0, 2.0)
        assertEquals(2.0 * 2.0, order.total, 1e-7)

        order = Order(currencyPair, 10.0, 2.5)
        assertEquals(10.0 * 2.5, order.total, 1e-7)

        order = Order(currencyPair, 0.0, 2.0)
        assertEquals(0.0, order.total, 1e-7)

        order = Order(currencyPair, 999.0, 9999.0)
        assertEquals(999.0 * 9999.0, order.total, 1e-7)
    }
}
