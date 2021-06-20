package ru.maxultra.wstask.domain.usecases

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PairOfListsToListOfPairsUseCaseTests {

    @Test
    fun integerListsTest() {
        val firstSize = 100
        val secondSize = firstSize * 2

        val firstInit: (Int) -> Int = { it }
        val secondInit: (Int) -> Int = { secondSize - it }

        val firstList = Array(firstSize, firstInit).toList()
        val secondList = Array(secondSize, secondInit).toList()

        val expectedWhenSecondIsLarger = Array<Pair<Int?, Int?>>(secondSize) {
            val first = if (it < firstSize) firstInit(it) else null
            val second = secondInit(it)
            Pair(first, second)
        }.toList()

        assertEquals(
            expectedWhenSecondIsLarger,
            PairOfListsToListOfPairsUseCase.execute(Pair(firstList, secondList))
        )

        val expectedWhenFirstIsLarger = Array<Pair<Int?, Int?>>(secondSize) {
            val first = secondInit(it)
            val second = if (it < firstSize) firstInit(it) else null
            Pair(first, second)
        }.toList()

        assertEquals(
            expectedWhenFirstIsLarger,
            PairOfListsToListOfPairsUseCase.execute(Pair(secondList, firstList))
        )

        val expectedWhenSameSize = firstList.map { Pair(it, it) }

        assertEquals(
            expectedWhenSameSize,
            PairOfListsToListOfPairsUseCase.execute(Pair(firstList, firstList))
        )

        assertTrue(
            PairOfListsToListOfPairsUseCase.execute(Pair(firstList, firstList))
                .none { it.first == null || it.second == null }
        )
    }
}
