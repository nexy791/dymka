package com.ribsky.intro.ui

import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.intro.adapter.ViewPagerAdapter
import com.ribsky.intro.databinding.ActivityIntroBinding
import com.ribsky.intro.ui.fragments.success.IntroSuccessFragment
import com.ribsky.intro.ui.fragments.goal.IntroGoalFragment
import com.ribsky.intro.ui.fragments.level.IntroLevelFragment
import com.ribsky.intro.utils.IntroCallback
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity :
    BaseActivity<IntroViewModel, ActivityIntroBinding>(ActivityIntroBinding::inflate),
    IntroCallback {
    override val viewModel: IntroViewModel by viewModel()

    private var viewPagerAdapter: ViewPagerAdapter? = null

    override fun initView() {
        Analytics.logEvent(Analytics.Event.INTRO_OPEN)
        initViewPager()
    }

    private fun initViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this).apply {
            addFragment(IntroGoalFragment.newInstance())
            addFragment(IntroLevelFragment.newInstance())
            addFragment(IntroSuccessFragment.newInstance())
        }
        val pager = BookFlipPageTransformer2().apply {
            isEnableScale = true
            scaleAmountPercent = 10f
        }
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            setPageTransformer(pager)
            isUserInputEnabled = false
        }
    }

    override fun initObs() {

    }

    override fun clear() {

    }

    override fun onBackPressed() {
        showExitAlert(
            positiveAction = { finish() },
            negativeAction = { }
        )
    }

    override fun next() {
        if (binding.viewPager.currentItem == viewPagerAdapter!!.itemCount - 1) finish()
        else binding.viewPager.currentItem++
    }


}