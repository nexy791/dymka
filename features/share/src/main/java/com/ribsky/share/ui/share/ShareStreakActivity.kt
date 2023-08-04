package com.ribsky.share.ui.share

import com.ribsky.analytics.Analytics
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert
import com.ribsky.navigation.features.ShareStreakNavigation
import com.ribsky.share.databinding.ActivityShareStreakBinding
import com.ribsky.share.ui.base.BaseShareActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShareStreakActivity :
    BaseShareActivity<ShareStreakViewModel, ActivityShareStreakBinding>(
        ActivityShareStreakBinding::inflate
    ) {

    override val viewModel: ShareStreakViewModel by viewModel()

    private val streak by lazy {
        intent.getParcelableExtra<ShareStreakNavigation.Params>(ShareStreakNavigation.KEY_SHARE_STREAK)!!
    }

    override fun initView() {
        Analytics.logEvent(Analytics.Event.STREAK_OPEN)
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
            share(imageContainer, Analytics.Event.SHARE_STREAK)
        }
        btnMore.apply {
            setOnClickListener {
                if (streak.isDone) {
                    showAlert(
                        title = "Все супер!",
                        message = "Ти зберігаєш свою серію, але якщо ти не виконуватимеш завдання, то вона зникне 😔",
                        positiveButton = "Добре",
                        positiveAction = {},
                        negativeButton = "Закрити",
                        negativeAction = {}
                    )
                } else {
                    showAlert(
                        title = "Ще не пізно!",
                        message = "Ти можеш виконати завдання і зберегти свою серію 😎",
                        positiveButton = "Добре",
                        positiveAction = {},
                        negativeButton = "Закрити",
                        negativeAction = {}
                    )
                }
            }
            text = if (streak.isDone) "Сьогодні виконано ✅" else "Сьогодні не виконано ❎"
        }
        tvDescription.text = streak.count.toString()
    }

    override fun initObs() = with(viewModel) {}
}
