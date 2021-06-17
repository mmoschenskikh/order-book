package ru.maxultra.wstask.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.maxultra.wstask.databinding.ActivityMainBinding
import ru.maxultra.wstask.presentation.main.listeners.BottomNavItemSelectedListener
import ru.maxultra.wstask.presentation.main.listeners.PageSelectedListener
import ru.maxultra.wstask.presentation.main.viewpager.ViewPagerAdapter
import ru.maxultra.wstask.presentation.main.viewpager.ViewPagerOnPageChangeCallback

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
    }
}
