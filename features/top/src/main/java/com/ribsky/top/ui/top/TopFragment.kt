package com.ribsky.top.ui.top

import com.google.android.material.tabs.TabLayoutMediator
import com.ribsky.common.base.BaseFragment
import com.ribsky.top.adapter.pager.BaseTopViewPagerAdapter
import com.ribsky.top.adapter.pager.TopViewPagerAdapter
import com.ribsky.top.databinding.FragmentTopBinding
import com.ribsky.top.ui.stars.TopStarsFragment
import com.ribsky.top.ui.streak.TopStreakFragment
import com.ribsky.top.ui.tests.TopTestsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopFragment : BaseFragment<TopViewModel, FragmentTopBinding>(FragmentTopBinding::inflate) {
    override val viewModel: TopViewModel by viewModel()

    private var adapter: BaseTopViewPagerAdapter? = null

    override fun initView() {
        initAdapter()
        initPager()
    }

    private fun initAdapter() {
        adapter = TopViewPagerAdapter(this).apply {
            setTitles(listOf("Дні \uD83D\uDD25", "Зірки ⭐", "Тести \uD83D\uDCDA"))
            setFragments(
                listOf(
                    TopStreakFragment.newInstance(),
                    //   TopLessonsFragment.newInstance(),
                    TopStarsFragment.newInstance(),
                    TopTestsFragment.newInstance(),
                )
            )
        }
    }

    private fun initPager() = with(binding) {
        pager.adapter = adapter
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = adapter?.createTitle(position)
        }.attach()
    }

    override fun initObs() {
    }

    override fun clear() {
        adapter = null
    }
}
