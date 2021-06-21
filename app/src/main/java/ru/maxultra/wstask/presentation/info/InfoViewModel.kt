package ru.maxultra.wstask.presentation.info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import ru.maxultra.wstask.domain.entities.Order
import ru.maxultra.wstask.domain.usecases.GetDataFlowsUseCase
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val useCase: GetDataFlowsUseCase) : ViewModel() {

    var type: InfoType = InfoType.BIDS

    val dataIsReady = useCase.dataIsReady.asLiveData()
    val currencyPair = useCase.currentSymbol.asLiveData()

    var data: LiveData<List<Order>> = emptyFlow<List<Order>>().asLiveData()
        private set

    fun unsubscribe() {
        data = emptyFlow<List<Order>>().asLiveData()
    }

    fun subscribe() {
        Log.d("InfoViewModel", "Type is $type")
        data = if (type == InfoType.BIDS) useCase.bids.asLiveData() else useCase.asks.asLiveData()
    }
}


