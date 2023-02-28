package com.ribsky.top.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class BaseTopViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    abstract fun setFragments(list: List<Fragment>)
    abstract fun setTitles(listNames: List<String>)
    abstract fun createTitle(position: Int): String
}
