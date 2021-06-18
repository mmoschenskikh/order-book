package ru.maxultra.wstask.presentation.info.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.maxultra.wstask.R
import ru.maxultra.wstask.databinding.ItemOrderBinding
import ru.maxultra.wstask.domain.entities.Order
import ru.maxultra.wstask.presentation.round

class OrderViewHolder(
    private val binding: ItemOrderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order, position: Int) {
        binding.amount.text = order.baseAmount.round(6).toString()
        binding.price.text = order.quotePrice.round(2).toString()
        binding.total.text = order.total.round(5).toString()

        val background =
            if (position % 2 == 0)
                R.color.light_grey
            else
                R.color.white
        binding.root.setBackgroundResource(background)
    }
}
