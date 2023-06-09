package com.ribsky.share.ui.story

import com.ribsky.analytics.Analytics
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.copy
import com.ribsky.share.databinding.ActivityShareStoryBinding
import com.ribsky.share.ui.base.BaseShareActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShareStoryActivity :
    BaseShareActivity<ShareStoryViewModel, ActivityShareStoryBinding>(
        ActivityShareStoryBinding::inflate
    ) {

    override val viewModel: ShareStoryViewModel by viewModel()

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
            share(imageContainer, Analytics.Event.SHARE_STORY)
            copy("@dymka.me")
        }
        btnMore.setOnClickListener {
            showAlert(
                title = "Інші способи",
                message = "Якщо в тебе немає Instagram, то можеш поділитись зі своїми друзями за допомогою інших соціальних мереж та надіслати скріншот на пошту",
                positiveButton = "Пошта",
                positiveAction = {
                    sendEmail(
                        subject = "Думка знижка",
                        text = "Привіт, я хочу отримати знижку на підписку в Думці, але у мене немає Instagram. Чи можна отримати знижку за скріншот?",
                    )
                },
                negativeButton = "Відмінити",
                negativeAction = {},
                cancelable = true
            )
        }
    }

    override fun initObs() = with(viewModel) {}
}
