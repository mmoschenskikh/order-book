package ru.maxultra.wstask.presentation.details.recyclerview

import androidx.recyclerview.widget.RecyclerView
import ru.maxultra.wstask.databinding.ItemDiffBinding
import ru.maxultra.wstask.domain.entities.Difference

class DiffViewHolder(
    private val binding: ItemDiffBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bidDiff: Difference, askDiff: Difference) {
        binding.bidPrice.text = String.format("%.2f", bidDiff.price)
        binding.bidDiff.text = String.format("%.6f", bidDiff.diff)
        binding.askPrice.text = String.format("%.2f", askDiff.price)
        binding.askDiff.text = String.format("%.6f", askDiff.diff)
    }
}
