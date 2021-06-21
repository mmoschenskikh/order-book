package ru.maxultra.wstask.presentation.main.viewpager

import androidx.viewpager2.widget.ViewPager2
import ru.maxultra.wstask.presentation.main.MainViewModel

class ViewPagerOnPageChangeCallback(
    private val viewModel: MainViewModel
) : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        val page = when (position) {
            0 -> PageType.INFO_BID
            1 -> PageType.INFO_ASK
            else -> PageType.DETAILS
        }
        viewModel.selectPage(page)
    }
}
