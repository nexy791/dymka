package com.ribsky.share.ui.word

import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import com.ribsky.analytics.Analytics
import com.ribsky.common.livedata.Resource
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.navigation.features.ShareWordNavigation.Companion.KEY_SHARE_WORD
import com.ribsky.share.databinding.ActivityShareBinding
import com.ribsky.share.ui.base.BaseShareActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShareWordActivity :
    BaseShareActivity<ShareWordViewModel, ActivityShareBinding>(
        ActivityShareBinding::inflate
    ) {

    private val wordId by lazy {
        intent.extras!!.getInt(KEY_SHARE_WORD)
    }

    override val viewModel: ShareWordViewModel by viewModel()

    override fun initView() {
        initToolbar()
        initBtns()
    }

    private fun initToolbar() = with(binding) {
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initBtns() = with(binding) {
        btnNext.setOnClickListener {
            share(imageContainer, Analytics.Event.SHARE_WORDS)
        }
        btnWidget.setOnClickListener {
            // showBottomSheetDialog(AddWidgetFactory { addWidgetToHomeScreen(this@ShareWordActivity) }.createDialog())
        }
        btnWidget.isGone = true
        // isWidgetAdded(this@ShareWordActivity)
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
                Resource.Status.ERROR -> showErrorDialog(it.exception?.localizedMessage) { finish() }
            }
        }
    }
}
