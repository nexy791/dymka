package com.ribsky.account.dialog.profile

import androidx.core.view.isGone
import com.ribsky.account.dialog.base.BaseProfileDialog
import com.ribsky.account.utils.ext.AlertExt.Companion.flagUser
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ViewExt.Companion.formatDays
import com.ribsky.navigation.features.ProfileNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileDialog : BaseProfileDialog() {

    override val viewModel: ProfileViewModel by viewModel()

    private val profileId: Int by lazy { arguments?.getInt(ProfileNavigation.KEY_ID)!! }

    override fun initViews() = with(binding) {
        super.initViews()
        initUiSettings()
    }

    override fun initObserves() = with(viewModel) {
        super.initObserves()
        getProfile(profileId)
    }

    private fun initUiSettings() = with(binding.container) {
        binding.fabFlag.setOnClickListener { flagUser(viewModel.userModel!!.name) }
        btnSettings.isGone = true
        cardRate.isGone = true
        tvEmail.isGone = true
    }

    override fun updateDaysInfo(days: Int, isChecked: Boolean) = with(binding.container) {
        tvDay.text = days.formatDays()
        checkbox.setImageResource(if (isChecked) commonDrawable.ic_round_check_circle_24 else commonDrawable.ic_round_check_circle_outline_24)
        checkbox.isGone = true
        materialTextView.text = "Дні поспіль користувача"
        icNext.isGone = true
    }

    override fun updatePremInfo(isPrem: Boolean) = with(binding.container) {
        cardShop.isGone = !isPrem
        btnShop.text = "Підтримати"
        if (isPrem) tvDescriptionPreminum.text = "Цей користувач має Преміум-підписку"
    }
}
