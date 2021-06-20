package ru.maxultra.wstask.presentation.info.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.maxultra.wstask.databinding.ItemOrderBinding
import ru.maxultra.wstask.domain.entities.Order

class OrderViewHolder(
    private val binding: ItemOrderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order) {
        binding.amount.text = String.format("%.6f", order.baseAmount)
        binding.price.text = String.format("%.2f", order.quotePrice)
        binding.total.text = String.format("%.5f", order.total)
    }
}
