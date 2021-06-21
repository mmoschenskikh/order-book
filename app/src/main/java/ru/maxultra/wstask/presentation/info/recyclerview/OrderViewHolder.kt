package ru.maxultra.wstask.presentation.info.recyclerview

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.maxultra.wstask.R
import ru.maxultra.wstask.databinding.ItemOrderBinding
import ru.maxultra.wstask.domain.entities.Order
import ru.maxultra.wstask.presentation.formatDouble
import ru.maxultra.wstask.presentation.info.InfoType

class OrderViewHolder(
    private val binding: ItemOrderBinding,
    type: InfoType
) : RecyclerView.ViewHolder(binding.root) {

    @ColorRes
    private val priceColor = if (type == InfoType.BIDS) R.color.bid_green else R.color.ask_red

    fun bind(order: Order) {
        binding.amount.text = formatDouble(order.baseAmount)
        binding.price.text = formatDouble(order.quotePrice)
        binding.price.setTextColor(ContextCompat.getColor(binding.root.context, priceColor))
        binding.total.text = formatDouble(order.total)
    }
}
