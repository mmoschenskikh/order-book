package ru.maxultra.wstask.domain.usecases

import ru.maxultra.wstask.domain.Repository
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair
import javax.inject.Inject

class ManageCurrencyPairsUseCase @Inject constructor(private val repository: Repository) {
    fun getAvailablePairs() = repository.getAvailablePairs()
    suspend fun setCurrencyPair(pair: CurrencyPair) = repository.setCurrencyPair(pair)
}
