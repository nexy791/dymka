package com.ribsky.settings.ui.settings

import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.ActionExt.Companion.openAppPage
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.ActionExt.Companion.shareApp
import com.ribsky.navigation.features.LibraryNavigation
import com.ribsky.navigation.features.LoaderNavigation
import com.ribsky.navigation.features.SettingsNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.settings.adapter.settings.SettingsAdapter
import com.ribsky.settings.databinding.ActivitySettingsBinding
import com.ribsky.settings.model.Settings
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity :
    BaseActivity<SettingsNavigation, SettingsViewModel, ActivitySettingsBinding>(
        ActivitySettingsBinding::inflate
    ) {

    override val viewModel: SettingsViewModel by viewModel()
    override val navigation: SettingsNavigation by inject()

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
            Settings.Type.SUPPORT -> navigation.navigateShop(shopNavigation)
            Settings.Type.CONTACT -> contact()
            Settings.Type.RATE -> openAppPage()
            Settings.Type.UPDATE -> openAppPage()
            Settings.Type.EXIT -> viewModel.signOut()
            Settings.Type.DELETE -> delete()
            Settings.Type.LIBRARY -> navigation.navigateLibrary(libraryNavigation)
        }
    }

    private fun contact() {
        sendEmail(
            subject = "??????????????????",
            text = ""
        )
    }

    private fun delete() {
        sendEmail(
            subject = "???????????????? ????????????",
            text = ""
        )
    }

    override fun initObs() = with(viewModel) {
        status.observe(this@SettingsActivity) {
            if (it) {
                navigation.navigateLoad(loaderNavigation)
            }
        }
    }

    override fun clear() {
        mAdapter = null
    }
}
