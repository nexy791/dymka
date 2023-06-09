package com.ribsky.account.dialog.profile

import androidx.core.view.isGone
import com.ribsky.account.dialog.base.BaseProfileDialog
import com.ribsky.account.utils.ext.AlertExt.Companion.flagUser
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
        binding.fabFlag.setOnClickListener { requireActivity().flagUser(viewModel.user!!.name) }
        btnSettings.isGone = true
        cardRate.isGone = true
        tvEmail.isGone = true
    }

    override fun updatePremInfo(isPrem: Boolean) = with(binding.container) {
        cardShop.isGone = !isPrem
        btnShop.text = "Підтримати проєкт"
        if (isPrem) tvDescriptionPreminum.text = "Цей користувач має Premium-підписку"
    }
}
