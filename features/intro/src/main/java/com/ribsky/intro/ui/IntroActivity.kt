package com.ribsky.intro.ui

import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.intro.adapter.ViewPagerAdapter
import com.ribsky.intro.databinding.ActivityIntroBinding
import com.ribsky.intro.ui.fragments.from.IntroFromFragment
import com.ribsky.intro.ui.fragments.goal.IntroGoalFragment
import com.ribsky.intro.ui.fragments.level.IntroLevelFragment
import com.ribsky.intro.ui.fragments.success.IntroSuccessFragment
import com.ribsky.intro.utils.IntroCallback
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
            if (viewModel.getGoal() == null) addFragment(IntroGoalFragment.newInstance())
            if (viewModel.getLevel() == null) addFragment(IntroLevelFragment.newInstance())
            if (viewModel.getFrom() == null) addFragment(IntroFromFragment.newInstance())
            addFragment(IntroSuccessFragment.newInstance())
        }
        binding.viewPager.apply {
            adapter = viewPagerAdapter
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
        else binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
    }


}