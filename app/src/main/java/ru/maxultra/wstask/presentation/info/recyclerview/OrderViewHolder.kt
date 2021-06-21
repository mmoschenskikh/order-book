package ru.maxultra.wstask.presentation.info.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.maxultra.wstask.databinding.ItemOrderBinding
import ru.maxultra.wstask.domain.entities.Order
import kotlin.math.ceil
import kotlin.math.log10

class OrderViewHolder(
    private val binding: ItemOrderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order) {
        binding.amount.text = formatDouble(order.baseAmount)
        binding.price.text = formatDouble(order.quotePrice)
        binding.total.text = formatDouble(order.total)
    }

    private fun formatDouble(double: Double): String {
        if (double < 1)
            return String.format("%.6f", double)
        var digits = 7 - ceil(log10(double)).toInt()
        if (digits < 0) digits = 0
        return String.format("%.${digits}f", double)
    }
}
