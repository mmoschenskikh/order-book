package ru.maxultra.wstask.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _pageSelected = MutableLiveData(PageType.INFO_BID)
    val pageSelected: LiveData<PageType>
        get() = _pageSelected

    fun selectPage(type: PageType) {
        if (type != pageSelected.value) {
            _pageSelected.value = type
        }
    }

}

