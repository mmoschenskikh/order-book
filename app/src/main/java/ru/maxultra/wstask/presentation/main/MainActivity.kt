package ru.maxultra.wstask.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.maxultra.wstask.databinding.ActivityMainBinding
import ru.maxultra.wstask.presentation.main.dropdown.CurrencyPairAdapter
import ru.maxultra.wstask.presentation.main.dropdown.SpinnerOnItemSelectedListener
import ru.maxultra.wstask.presentation.main.listeners.BottomNavItemSelectedListener
import ru.maxultra.wstask.presentation.main.listeners.PageSelectedListener
import ru.maxultra.wstask.presentation.main.viewpager.ViewPagerAdapter
import ru.maxultra.wstask.presentation.main.viewpager.ViewPagerOnPageChangeCallback

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnNavigationItemSelectedListener(
            BottomNavItemSelectedListener(viewModel)
        )

        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.viewPager.registerOnPageChangeCallback(ViewPagerOnPageChangeCallback(viewModel))

        val pageSelectedListener = PageSelectedListener(binding.bottomNav, binding.viewPager)
        viewModel.pageSelected.observe(this) { pageSelectedListener.onPageSelected(it) }

        viewModel.dropdownOptions.observe(this) { options ->
            val dropdownAdapter = CurrencyPairAdapter(this, options ?: emptyList())
            binding.currencyDropdown.adapter = dropdownAdapter
        }

        binding.currencyDropdown.onItemSelectedListener =
            SpinnerOnItemSelectedListener { viewModel.getData(it) }

        viewModel.status.observe(this) {
            when (it) {
                Status.LOADING -> {
                    binding.errorMessage.visibility = View.GONE
                    binding.loadingIndicator.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.loadingIndicator.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.loadingIndicator.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
    }
}
