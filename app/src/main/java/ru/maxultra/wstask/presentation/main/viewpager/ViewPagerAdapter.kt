package ru.maxultra.wstask.presentation.main.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.maxultra.wstask.presentation.info.InfoFragment
import ru.maxultra.wstask.presentation.main.PageType

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = PageType.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoFragment() // FIXME
            1 -> InfoFragment() // FIXME
            2 -> InfoFragment() // FIXME
            else -> throw IllegalArgumentException("No tab expected at position #$position")
        }
    }
}
