package com.ribsky.share.ui.word

import androidx.core.text.parseAsHtml
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.Const.Companion.TEXT_SHARE
import com.ribsky.common.utils.share.ShareImage
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.navigation.features.ShareWordNavigation
import com.ribsky.navigation.features.ShareWordNavigation.Companion.KEY_SHARE_WORD
import com.ribsky.share.databinding.ActivityShareBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShareWordActivity :
    BaseActivity<ShareWordNavigation, ShareWordViewModel, ActivityShareBinding>(ActivityShareBinding::inflate) {

    private val wordId by lazy {
        intent.extras!!.getBundle(KEY_SHARE_WORD)?.getInt(KEY_SHARE_WORD)!!
    }

    private var shareImage: ShareImage? = null
    override val viewModel: ShareWordViewModel by viewModel()
    override val navigation: ShareWordNavigation by inject()

    override fun initView() {
        initToolbar()
        initBtns()
    }

    private fun initToolbar() = with(binding) {
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initShareView() = with(binding) {
        shareImage = ShareImage
            .addContext(this@ShareWordActivity)
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
        Analytics.logEvent(Analytics.Event.SHARE_WORDS)
        initShareView()
        shareImage?.share()
    }

    private fun showLoading() {
    }

    private fun showSuccess(word: BaseBestWordModel) = with(binding) {
        tvTitle.text = word.title.parseAsHtml()
        tvDescription.text = word.description.parseAsHtml()
    }

    override fun initObs() = with(viewModel) {
        getBestWord(wordId)
        getBestWord.observe(this@ShareWordActivity) {
            when (it.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> showSuccess(it.data!!)
                Resource.Status.ERROR -> TODO()
            }
        }
    }

    override fun clear() {
        shareImage?.clear()
        shareImage = null
    }
}
