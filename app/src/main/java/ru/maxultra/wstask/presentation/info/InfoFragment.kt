package ru.maxultra.wstask.presentation.info

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import ru.maxultra.wstask.R
import ru.maxultra.wstask.databinding.FragmentInfoBinding
import ru.maxultra.wstask.domain.entities.currencypair.CurrencyPair
import ru.maxultra.wstask.domain.usecases.CurrencyToStringUseCase
import ru.maxultra.wstask.presentation.base.BaseFragment
import ru.maxultra.wstask.presentation.info.recyclerview.OrderListAdapter

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

    private lateinit var viewModel: InfoViewModel
    private lateinit var type: InfoType
    private lateinit var adapter: OrderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pageTypeOrdinal = arguments?.getInt(ARG_PAGE_TYPE)
            ?: savedInstanceState?.getInt(ARG_PAGE_TYPE)
            ?: throw IllegalStateException("No page type provided")

        type = InfoType.values()[pageTypeOrdinal]
        adapter = OrderListAdapter(type)

        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        viewModel.type = type
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        viewModel.dataIsReady.observe(viewLifecycleOwner) {
            if (it) {
                subscribe()
            } else {
                unsubscribe()
            }
        }

        viewModel.currencyPair.observe(viewLifecycleOwner) {
            if (it != null) setHeader(it)
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

    private fun setHeader(currencyPair: CurrencyPair) {
        val baseCurrency = currencyPair.baseCurrency
        val baseAsString = CurrencyToStringUseCase.execute(baseCurrency)
        binding.header.amount.text = getString(R.string.amount_header, baseAsString)
        val quoteCurrency = currencyPair.quoteCurrency
        val quoteAsString = CurrencyToStringUseCase.execute(quoteCurrency)
        binding.header.price.text = getString(R.string.price_header, quoteAsString)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val intPageType = type.ordinal
        outState.putInt(ARG_PAGE_TYPE, intPageType)
    }

    companion object {
        private const val ARG_PAGE_TYPE = "info_fragment_page_type"

        fun newInstance(type: InfoType): InfoFragment {
            val intPageType = type.ordinal
            val args = bundleOf(ARG_PAGE_TYPE to intPageType)
            val fragment = InfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

