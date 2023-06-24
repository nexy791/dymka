package com.ribsky.account.dialog.account

import androidx.core.view.isGone
import com.ribsky.account.dialog.base.BaseProfileDialog
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.chip.ChipBuilder.Companion.createChip
import com.ribsky.common.utils.ext.ViewExt.Companion.formatDays
import com.ribsky.navigation.features.ShareStreakNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountDialog : BaseProfileDialog() {

    override val viewModel: AccountViewModel by viewModel()

    private val shareStreakNavigation: ShareStreakNavigation by inject()

    override fun initObserves() = with(viewModel) {
        super.initObserves()
        getProfile()
    }

    override fun updateDaysInfo(days: Int, isChecked: Boolean) = with(binding.container) {
        tvDay.text = days.formatDays()
        checkbox.setImageResource(if (isChecked) commonDrawable.ic_round_check_circle_24 else commonDrawable.ic_round_check_circle_outline_24)
        checkbox.isGone = false
        materialTextView.text = "Твої дні поспіль"
        materialCardView2.setOnClickListener {
            shareStreakNavigation.navigate(
                requireContext(),
                ShareStreakNavigation.Params(
                    days,
                    isChecked
                )
            )
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()
        initFab()
    }


    private fun initFab() = with(binding) {
        fabFlag.isGone = true
    }

    override fun updatePremInfo(isPrem: Boolean) = with(binding.container) {
        if (isPrem) {
            tvDescriptionPreminum.text = "Дякуємо за підтримку!"
        } else {
            tvDescriptionPreminum.text = "Отримай доступ до\nвсіх курсів та тестів"
        }
        btnShop.text = "Керувати"
    }
}
