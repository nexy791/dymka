package com.ribsky.intro.ui.fragments.success

import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseFragment
import com.ribsky.intro.databinding.FragmentIntroSuccessBinding
import com.ribsky.intro.ui.fragments.BaseViewModel
import com.ribsky.intro.utils.IntroCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroSuccessFragment :
    BaseFragment<BaseViewModel, FragmentIntroSuccessBinding>(FragmentIntroSuccessBinding::inflate) {

    override val viewModel: BaseViewModel by viewModel()

    private var introCallback: IntroCallback? = null

    override fun initView() {
        introCallback = activity as IntroCallback
        binding.btnNext.setOnClickListener {
            Analytics.logEvent(Analytics.Event.INTRO_DONE)
            introCallback?.next()
        }
    }

    override fun initObs() {

    }

    override fun clear() {

    }


    companion object {
        fun newInstance() = IntroSuccessFragment()
    }

}