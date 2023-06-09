package com.ribsky.share.ui.message

import androidx.core.text.parseAsHtml
import com.ribsky.analytics.Analytics
import com.ribsky.navigation.features.ShareMessageNavigation
import com.ribsky.share.databinding.ActivityShareMessageBinding
import com.ribsky.share.ui.base.BaseShareActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShareMessageActivity :
    BaseShareActivity<ShareViewModel, ActivityShareMessageBinding>(
        ActivityShareMessageBinding::inflate
    ) {

    override val viewModel: ShareViewModel by viewModel()

    private val messageText by lazy {
        intent.extras?.getString(ShareMessageNavigation.KEY_SHARE_MESSAGE_ID)
    }

    override fun initView() {
        initToolbar()
        initTexts()
        initBtns()
    }

    private fun initToolbar() = with(binding) {
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initTexts() = with(binding) {
        text.text = messageText?.parseAsHtml()
    }

    private fun initBtns() = with(binding) {
        btnNext.setOnClickListener {
            share(imageContainer, Analytics.Event.SHARE_SMS)
        }
    }

    override fun initObs() {
    }
}
