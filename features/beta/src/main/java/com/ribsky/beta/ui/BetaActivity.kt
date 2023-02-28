package com.ribsky.beta.ui

import com.ribsky.beta.databinding.ActivityBetaBinding
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.ActionExt.Companion.openAppPage
import com.ribsky.common.utils.ext.ViewExt.Companion.toast
import com.ribsky.navigation.features.BetaNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BetaActivity :
    BaseActivity<BetaNavigation, BetaViewModel, ActivityBetaBinding>(
        ActivityBetaBinding::inflate
    ) {

    override val viewModel: BetaViewModel by viewModel()
    override val navigation: BetaNavigation by inject()

    override fun initView() {
        initBtns()
    }

    private fun initBtns() = with(binding) {
        btnRate.setOnClickListener {
            openAppPage()
            sayThanks()
            finish()
        }
        btnClose.setOnClickListener {
            finish()
        }
    }

    private fun sayThanks() {
        toast("Дякуємо! \uD83D\uDC99\uD83D\uDC9B")
    }

    override fun initObs() {}
    override fun clear() {}
}
