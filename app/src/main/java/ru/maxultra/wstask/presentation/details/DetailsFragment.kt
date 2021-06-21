package ru.maxultra.wstask.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.maxultra.wstask.databinding.FragmentDetailsBinding
import ru.maxultra.wstask.presentation.base.BaseFragment
import ru.maxultra.wstask.presentation.details.recyclerview.DiffListAdapter

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val adapter = DiffListAdapter()
    private val viewModel by activityViewModels<DetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null

        viewModel.dataIsReady.observe(viewLifecycleOwner) {
            if (it) {
                subscribe()
            } else {
                unsubscribe()
            }
        }
    }

    private fun unsubscribe() {
        viewModel.data.removeObservers(viewLifecycleOwner)
        adapter.submitList(emptyList())
        viewModel.unsubscribe()
    }

    private fun subscribe() {
        viewModel.subscribe()
        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
