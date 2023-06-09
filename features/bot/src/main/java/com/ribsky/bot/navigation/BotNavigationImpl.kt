package com.ribsky.bot.navigation

import android.content.Context
import android.content.Intent
import com.ribsky.bot.ui.BotActivity
import com.ribsky.navigation.features.BotNavigation

class BotNavigationImpl : BotNavigation {

    override fun navigate(navigation: Context) {
        navigation.startActivity(Intent(navigation, BotActivity::class.java))
    }
}
