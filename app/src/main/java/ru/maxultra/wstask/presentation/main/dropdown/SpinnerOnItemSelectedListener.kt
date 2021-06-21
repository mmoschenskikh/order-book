package ru.maxultra.wstask.presentation.main.dropdown

import android.view.View
import android.widget.AdapterView
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair

class SpinnerOnItemSelectedListener(
    private val onSelect: (CurrencyPair) -> Unit
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        val item = parent.getItemAtPosition(position) as CurrencyPair
        onSelect(item)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        parent.setSelection(0)
    }
}
