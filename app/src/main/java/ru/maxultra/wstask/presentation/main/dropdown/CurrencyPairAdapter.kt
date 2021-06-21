package ru.maxultra.wstask.presentation.main.dropdown

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.maxultra.wstask.R
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair
import ru.maxultra.wstask.domain.usecases.CurrencyToStringUseCase

class CurrencyPairAdapter(context: Context, list: List<CurrencyPair>) :
    ArrayAdapter<CurrencyPair>(context, 0, list) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: layoutInflater.inflate(R.layout.item_pair_dropdown, parent, false)

        val pair = getItem(position)
        setItemForPair(view, pair)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    private fun setItemForPair(view: View, pair: CurrencyPair?) {
        if (pair == null) return
        val base = view.findViewById<TextView>(R.id.base)
        val quote = view.findViewById<TextView>(R.id.quote)
        val baseCurrency = pair.baseCurrency
        val quoteCurrency = pair.quoteCurrency

        base.text = CurrencyToStringUseCase.execute(baseCurrency)
        quote.text = context.getString(
            R.string.quote_spinner_item,
            CurrencyToStringUseCase.execute(quoteCurrency)
        )
    }
}
