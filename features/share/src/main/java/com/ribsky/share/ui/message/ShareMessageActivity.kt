package com.ribsky.share.ui.message

import androidx.core.text.parseAsHtml
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.Const.Companion.TEXT_SHARE
import com.ribsky.common.utils.share.ShareImage
import com.ribsky.navigation.features.ShareMessageNavigation
import com.ribsky.share.databinding.ActivityShareMessageBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShareMessageActivity :
    BaseActivity<ShareMessageNavigation, ShareViewModel, ActivityShareMessageBinding>(
        ActivityShareMessageBinding::inflate
    ) {

    private var shareImage: ShareImage? = null
    override val viewModel: ShareViewModel by viewModel()
    override val navigation: ShareMessageNavigation by inject()

    private val messageText by lazy {
        intent.extras?.getBundle(ShareMessageNavigation.KEY_SHARE_MESSAGE)!!
            .getString(ShareMessageNavigation.KEY_SHARE_MESSAGE_ID)
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

    private fun initShareView() = with(binding) {
        shareImage = ShareImage
            .addContext(this@ShareMessageActivity)
            .addView(imageContainer)
            .addDescription(TEXT_SHARE)
            .build()
    }

    private fun initBtns() = with(binding) {
        btnNext.setOnClickListener {
            share()
        }
    }

    private fun share() {
        Analytics.logEvent(Analytics.Event.SHARE_SMS)
        initShareView()
        shareImage?.share()
    }

    override fun initObs() {
    }

    override fun clear() {
        shareImage?.clear()
    }
}
