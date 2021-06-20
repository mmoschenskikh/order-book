package ru.maxultra.wstask.domain.usecases

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order

class ComputeDiffUseCaseTests {

    @Test
    fun correctOrdersTest() {
        val price = 13579.02468
        var amount = 9999.999
        var oldOrder = Order(amount, price)
        for (i in -100 until 200) {
            amount -= i
            val newOrder = Order(amount, price)
            val diff = ComputeDiffUseCase.execute(oldOrder, newOrder)
            val expected = Difference(price, 0.0 - i)
            assertEquals(expected, diff)
            oldOrder = newOrder
        }
    }

    @Test
    fun incorrectOrdersTest() {
        val old = Order(123.9, 654.98)
        for (i in 0 until 10) {
            val new = Order(234.4, i.toDouble())
            assertThrows(IllegalArgumentException::class.java) {
                ComputeDiffUseCase.execute(old, new)
            }
        }
    }
}
