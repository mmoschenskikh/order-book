package ru.maxultra.wstask.domain.usecases

object PairOfListsToListOfPairsUseCase {

    fun <F, S> execute(pairOfLists: Pair<List<F>, List<S>>): List<Pair<F?, S?>> {
        val firstList = pairOfLists.first
        val secondList = pairOfLists.second
        val newListSize = maxOf(firstList.size, secondList.size)
        val newList = ArrayList<Pair<F?, S?>>(newListSize)
        for (i in 0 until newListSize) {
            val first = if (i < firstList.size) firstList[i] else null
            val second = if (i < secondList.size) secondList[i] else null
            newList.add(Pair(first, second))
        }
        return newList
    }
}
