package ru.maxultra.wstask.presentation.details.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.maxultra.wstask.databinding.ItemDiffBinding
import ru.maxultra.wstask.domain.entities.Difference

class DiffListAdapter : ListAdapter<Pair<Difference?, Difference?>, DiffViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Pair<Difference?, Difference?>>() {
                override fun areItemsTheSame(
                    oldItem: Pair<Difference?, Difference?>,
                    newItem: Pair<Difference?, Difference?>
                ): Boolean =
                    oldItem.first?.price == newItem.first?.price && oldItem.second?.price == newItem.second?.price

                override fun areContentsTheSame(
                    oldItem: Pair<Difference?, Difference?>,
                    newItem: Pair<Difference?, Difference?>
                ): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DiffViewHolder(
            ItemDiffBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DiffViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item.first, item.second)
    }
}
