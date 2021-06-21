package ru.maxultra.wstask.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.domain.usecases.GetDataFlowsUseCase
import ru.maxultra.wstask.domain.usecases.PairOfListsToListOfPairsUseCase
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val useCase: GetDataFlowsUseCase) : ViewModel() {

    val dataIsReady = useCase.dataIsReady.asLiveData()
    var data = emptyFlow<List<Pair<Difference?, Difference?>>>().asLiveData()
        private set

    fun unsubscribe() {
        data = emptyFlow<List<Pair<Difference?, Difference?>>>().asLiveData()
    }

    fun subscribe() {
        data = useCase.diff.map { pair ->
            withContext(Dispatchers.IO) {
                PairOfListsToListOfPairsUseCase.execute(pair)
            }
        }.asLiveData()
    }
}
