package com.ribsky.top.nav

import androidx.fragment.app.FragmentManager
import com.ribsky.navigation.features.TopDialogsNavigation
import com.ribsky.top.dialogs.down.DownDialog
import com.ribsky.top.dialogs.more.MoreDialog
import com.ribsky.top.dialogs.up.UpDialog

class TopDialogsNavigationImpl : TopDialogsNavigation {

    override fun navigate(navigation: FragmentManager, params: TopDialogsNavigation.Params) {
        try {
            when (params.status) {

                TopDialogsNavigation.Status.UP -> UpDialog.newInstance(
                    params.type,
                    params.users,
                    params.onDismiss
                ).show(navigation, "up")

                TopDialogsNavigation.Status.DOWN -> DownDialog.newInstance(
                    params.type,
                    params.users,
                    params.onDismiss
                ).show(navigation, "down")

                TopDialogsNavigation.Status.MORE -> MoreDialog.newInstance(
                    params.type,
                    params.users,
                    params.onDismiss
                ).show(navigation, "more")

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}