package com.ribsky.paywall.nav

import androidx.fragment.app.FragmentManager
import com.ribsky.navigation.features.PayWallNavigation
import com.ribsky.paywall.dialogs.paywall.PayWallDialog
import com.ribsky.paywall.dialogs.paywall.PayWallWelcomeDialog

class PayWallNavigationImpl : PayWallNavigation {

    override fun navigate(
        navigation: FragmentManager,
        params: PayWallNavigation.Params,
        callback: PayWallNavigation.Callback,
    ) {
        runCatching {
            if (navigation.isDestroyed) return
            if (navigation.findFragmentByTag(PayWallDialog.TAG) != null) return
            if (navigation.findFragmentByTag(PayWallWelcomeDialog.TAG) != null) return

            if (params.isWelcome) {
                PayWallWelcomeDialog.newInstance(params.date, callback)
                    .show(navigation, PayWallWelcomeDialog.TAG)
            } else {
                PayWallDialog.newInstance(params.date, callback)
                    .show(navigation, PayWallDialog.TAG)
            }

        }
    }

}