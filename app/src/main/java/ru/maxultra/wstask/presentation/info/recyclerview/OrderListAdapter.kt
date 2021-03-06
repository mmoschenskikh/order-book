package ru.maxultra.wstask.presentation.info.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.maxultra.wstask.databinding.ItemOrderBinding
import ru.maxultra.wstask.domain.entities.Order
import ru.maxultra.wstask.presentation.info.InfoType

class OrderListAdapter(val type: InfoType) : ListAdapter<Order, OrderViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order) =
                oldItem.quotePrice == newItem.quotePrice

            override fun areContentsTheSame(oldItem: Order, newItem: Order) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderViewHolder(
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            type
        )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) =
        holder.bind(getItem(position))
}
