package com.ribsky.settings.ui.settings

import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.ActionExt.Companion.openAppPage
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.ActionExt.Companion.shareApp
import com.ribsky.navigation.features.LibraryNavigation
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.settings.adapter.settings.SettingsAdapter
import com.ribsky.settings.databinding.ActivitySettingsBinding
import com.ribsky.settings.model.Settings
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity :
    BaseActivity<SettingsViewModel, ActivitySettingsBinding>(
        ActivitySettingsBinding::inflate
    ) {

    override val viewModel: SettingsViewModel by viewModel()

    private val shopNavigation: ShopNavigation by inject()
    private val libraryNavigation: LibraryNavigation by inject()
    private val loaderNavigation: LoaderNavigation by inject()

    private var mAdapter: SettingsAdapter? = null

    override fun initView() {
        initToolbar()
        initAdapter()
        initRecycler()
    }

    private fun initToolbar() = with(binding) {
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initAdapter() {
        mAdapter = SettingsAdapter {
            processClick(it)
        }
    }

    private fun initRecycler() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@SettingsActivity)
        adapter = mAdapter!!.apply {
            submitList(com.ribsky.settings.utils.Settings.list)
        }
    }

    private fun processClick(type: Settings.Type) {
        when (type) {
            Settings.Type.SHARE -> shareApp()
            Settings.Type.SUPPORT -> {
                Analytics.logEvent(Analytics.Event.PREMIUM_FROM_SETTINGS)
                shopNavigation.navigate(
                    this@SettingsActivity,
                    ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_SETTINGS)
                )
            }

            Settings.Type.CONTACT -> contact()
            Settings.Type.RATE -> openAppPage()
            Settings.Type.UPDATE -> openAppPage()
            Settings.Type.EXIT -> viewModel.signOut()
            Settings.Type.DELETE -> delete()
            Settings.Type.LIBRARY -> libraryNavigation.navigate(this@SettingsActivity)
        }
    }

    private fun contact() {
        sendEmail(
            subject = "Підтримка",
            text = ""
        )
    }

    private fun delete() {
        sendEmail(
            subject = "Видалити акаунт",
            text = ""
        )
    }

    override fun initObs() = with(viewModel) {
        status.observe(this@SettingsActivity) {
            if (it) loaderNavigation.navigate(this@SettingsActivity)
        }
    }

    override fun clear() {
        mAdapter = null
    }
}
