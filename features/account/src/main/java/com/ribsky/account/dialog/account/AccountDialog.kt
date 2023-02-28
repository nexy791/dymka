package com.ribsky.account.dialog.account

import androidx.core.view.isGone
import com.ribsky.account.dialog.base.BaseProfileDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountDialog : BaseProfileDialog() {

    override val viewModel: AccountViewModel by viewModel()

    override fun initObserves() = with(viewModel) {
        super.initObserves()
        getProfile()
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
