package ru.maxultra.wstask.presentation.main

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair
import ru.maxultra.wstask.domain.usecases.ManageCurrencyPairsUseCase
import ru.maxultra.wstask.presentation.main.viewpager.PageType
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: ManageCurrencyPairsUseCase) :
    ViewModel() {
    private val _pageSelected = MutableLiveData(PageType.INFO_BID)
    val pageSelected: LiveData<PageType>
        get() = _pageSelected

    private val _status = MutableLiveData(Status.LOADING)
    val status: LiveData<Status>
        get() = _status

    val dropdownOptions = useCase.getAvailablePairs().asLiveData()

    fun selectPage(type: PageType) {
        if (type != pageSelected.value) {
            _pageSelected.value = type
        }
    }

    fun getData(currencyPair: CurrencyPair) = viewModelScope.launch {
        try {
            _status.value = Status.LOADING
            useCase.setCurrencyPair(currencyPair)
            _status.value = Status.SUCCESS
        } catch (e: Exception) {
            _status.value = Status.ERROR
        }
    }
}

