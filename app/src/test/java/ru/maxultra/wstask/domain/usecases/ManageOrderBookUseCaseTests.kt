package ru.maxultra.wstask.domain.usecases

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order
import kotlin.random.Random

class ManageOrderBookUseCaseTests {

    private lateinit var useCase: ManageOrderBookUseCase
    private val localOrderBook = mutableListOf<Order>()

    @Before
    fun initialize() {
        for (i in 1..TEST_ORDER_BOOK_SIZE) {
            localOrderBook.add(Order(Random.nextDouble(), i.toDouble()))
        }
        useCase = ManageOrderBookUseCase(localOrderBook.toList())
    }

    @Test
    fun noNewDataTest() {
        val newData = emptyList<Order>()
        useCase.update(newData)
        assertEquals(
            localOrderBook.sortedBy { it.quotePrice },
            useCase.orderBook.sortedBy { it.quotePrice })
        assertEquals(emptyList<Difference>(), useCase.diff)
    }

    @Test
    fun someZeroItemsTest() {
        val newData = mutableListOf<Order>()
        for (i in 1..localOrderBook.size) {
            if (i % 4 == 0) {
                newData.add(Order(0.0, i.toDouble()))
            }
        }
        localOrderBook.removeAll { it.quotePrice.toInt() % 4 == 0 }
        useCase.update(newData)
        assertEquals(
            localOrderBook.sortedBy { it.quotePrice },
            useCase.orderBook.sortedBy { it.quotePrice })
        assertTrue(useCase.diff.all { it.price.toInt() % 4 == 0 })
    }

    @Test
    fun newItemsTest() {
        val newData = mutableListOf<Order>()
        for (i in 1..localOrderBook.size) {
            if (i % 3 == 0) {
                newData.add(Order(Random.nextDouble(), Random.nextDouble()))
            }
        }
        localOrderBook.addAll(newData)
        useCase.update(newData)
        assertEquals(
            localOrderBook.sortedBy { it.quotePrice },
            useCase.orderBook.sortedBy { it.quotePrice })
    }

    companion object {
        private const val TEST_ORDER_BOOK_SIZE = 100
    }
}
