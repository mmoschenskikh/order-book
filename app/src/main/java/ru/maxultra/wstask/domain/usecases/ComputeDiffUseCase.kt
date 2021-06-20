package ru.maxultra.wstask.domain.usecases

import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order

object ComputeDiffUseCase {

    fun execute(old: Order, new: Order): Difference {
        require(old.quotePrice == new.quotePrice) {
            "Orders prices must be equal to compute the difference."
        }
        val diff = new.baseAmount - old.baseAmount
        return Difference(new.quotePrice, diff)
    }
}
