package ru.maxultra.wstask.domain.entities

import org.junit.Assert.assertEquals
import org.junit.Test

class OrderTests {

    @Test
    fun orderTotalTest() {
        var order = Order(2.0, 2.0)
        assertEquals(2.0 * 2.0, order.total, 1e-7)

        order = Order(10.0, 2.5)
        assertEquals(10.0 * 2.5, order.total, 1e-7)

        order = Order(0.0, 2.0)
        assertEquals(0.0, order.total, 1e-7)

        order = Order(999.0, 9999.0)
        assertEquals(999.0 * 9999.0, order.total, 1e-7)
    }
}
