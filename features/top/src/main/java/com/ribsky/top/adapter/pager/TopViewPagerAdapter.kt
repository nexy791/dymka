package com.ribsky.top.adapter.pager

import androidx.fragment.app.Fragment

class TopViewPagerAdapter(fragment: Fragment) : BaseTopViewPagerAdapter(fragment) {

    private var list: List<Fragment> = emptyList()
    private var listNames: List<String> = emptyList()

    override fun setFragments(list: List<Fragment>) {
        this.list = list
    }

    override fun setTitles(listNames: List<String>) {
        this.listNames = listNames
    }

    override fun createFragment(position: Int): Fragment = list[position]

    override fun createTitle(position: Int): String = listNames[position]

    override fun getItemCount(): Int = list.size
}
