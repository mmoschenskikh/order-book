package ru.maxultra.wstask.presentation.details.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.maxultra.wstask.databinding.ItemDiffBinding
import ru.maxultra.wstask.domain.entities.Difference
import ru.maxultra.wstask.presentation.formatDouble

class DiffViewHolder(
    private val binding: ItemDiffBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bidDiff: Difference?, askDiff: Difference?) {
        if (bidDiff != null) {
            binding.bidPrice.text = formatDouble(bidDiff.price)
            binding.bidDiff.text = formatDouble(bidDiff.diff)
        }
        if (askDiff != null) {
            binding.askPrice.text = formatDouble(askDiff.price)
            binding.askDiff.text = formatDouble(askDiff.diff)
        }
    }
}
