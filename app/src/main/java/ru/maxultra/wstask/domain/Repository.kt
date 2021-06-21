package ru.maxultra.wstask.domain

import kotlinx.coroutines.flow.Flow
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair

interface Repository {

    val dataIsReady: Flow<Boolean>

    val currentSymbol: Flow<CurrencyPair?>

    fun getAvailablePairs(): Flow<List<CurrencyPair>>

    // Call setCurrencyPair before getting streams
    suspend fun setCurrencyPair(currencyPair: CurrencyPair)

    fun getBidsStream(): Flow<List<Order>>

    fun getAsksStream(): Flow<List<Order>>

    fun getDiffStream(): Flow<Pair<List<Difference>, List<Difference>>>
}
