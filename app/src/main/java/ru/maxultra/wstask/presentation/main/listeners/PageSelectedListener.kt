package ru.maxultra.wstask.presentation.main.listeners

import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.maxultra.wstask.R
import ru.maxultra.wstask.presentation.main.viewpager.PageType

class PageSelectedListener(
    private val navigationView: BottomNavigationView,
    private val viewPager: ViewPager2
) {
    fun onPageSelected(page: PageType) {
        @ColorRes val selectorId: Int
        @IdRes val selectedItemId: Int
        val viewPagerItem: Int
        when (page) {
            PageType.INFO_BID -> {
                selectorId = R.drawable.bottom_navigation_bid_color
                selectedItemId = R.id.show_bid
                viewPagerItem = 0
            }
            PageType.INFO_ASK -> {
                selectorId = R.drawable.bottom_navigation_ask_color
                selectedItemId = R.id.show_ask
                viewPagerItem = 1
            }
            PageType.DETAILS -> {
                selectorId = R.drawable.bottom_navigation_details_color
                selectedItemId = R.id.show_diff
                viewPagerItem = 2
            }
        }
        val colorStateList = ContextCompat.getColorStateList(navigationView.context, selectorId)
        navigationView.itemTextColor = colorStateList
        navigationView.itemIconTintList = colorStateList

        navigationView.selectedItemId = selectedItemId
        viewPager.currentItem = viewPagerItem
    }
}
