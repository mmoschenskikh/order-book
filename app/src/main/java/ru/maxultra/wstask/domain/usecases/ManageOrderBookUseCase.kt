package ru.maxultra.wstask.domain.usecases

import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order

class ManageOrderBookUseCase(initialState: List<Order>, private val orderBookMaxSize: Int = 150) {

    var orderBook = initialState
        private set
    var diff = emptyList<Difference>()
        private set

    fun update(newData: List<Order>) {
        val bookList = orderBook.toMutableList()
        val diffList = mutableListOf<Difference>()
        newData.forEach { order ->
            val elementIndex = orderBook.indexOfFirst { it.quotePrice == order.quotePrice }
            if (elementIndex != -1) {
                diffList.add(ComputeDiffUseCase.execute(orderBook[elementIndex], order))
                bookList[elementIndex] = order
            } else {
                if (bookList.size < orderBookMaxSize) {
                    bookList.add(0, order)
                }
            }
        }
        bookList.removeAll { it.baseAmount == 0.0 }

        orderBook = bookList
        diff = diffList
    }
}
