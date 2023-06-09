package com.ribsky.shop.nav

import android.content.Context
import android.content.Intent
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.shop.ui.ShopActivity

class ShopNavigationImpl : ShopNavigation {

    override fun navigate(navigation: Context) {
        navigation.startActivity(Intent(navigation, ShopActivity::class.java))
    }
}
