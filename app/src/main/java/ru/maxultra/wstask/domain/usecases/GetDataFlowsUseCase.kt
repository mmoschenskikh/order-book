package ru.maxultra.wstask.domain.usecases

import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.onEach
import ru.maxultra.wstask.domain.Repository
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.entities.Order
import javax.inject.Inject

class GetDataFlowsUseCase @Inject constructor(repository: Repository) {
    val currentSymbol = repository.currentSymbol
    val dataIsReady = repository.dataIsReady.onEach {
        if (it) {
            bids = repository.getBidsStream()
            asks = repository.getAsksStream()
            diff = repository.getDiffStream()
        } else {
            bids = emptyFlow()
            asks = emptyFlow()
            diff = emptyFlow()
        }
    }

    var bids = emptyFlow<List<Order>>()
        private set
    var asks = emptyFlow<List<Order>>()
        private set
    var diff = emptyFlow<Pair<List<Difference>, List<Difference>>>()
        private set
}
