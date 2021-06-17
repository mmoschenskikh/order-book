package ru.maxultra.wstask.presentation.main.listeners

import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.maxultra.wstask.R
import ru.maxultra.wstask.presentation.main.MainViewModel
import ru.maxultra.wstask.presentation.main.PageType

class BottomNavItemSelectedListener(
    private val viewModel: MainViewModel
) : BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val page = when (item.itemId) {
            R.id.show_bid -> PageType.INFO_BID
            R.id.show_ask -> PageType.INFO_ASK
            else -> PageType.DETAILS
        }
        viewModel.selectPage(page)
        return true
    }
}
