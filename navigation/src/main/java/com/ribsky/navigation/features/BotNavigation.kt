package com.ribsky.navigation.features

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.ribsky.navigation.base.Base
import com.ribsky.navigation.base.NavigationWithResultAndParams
import kotlinx.parcelize.Parcelize

interface BotNavigation :
    NavigationWithResultAndParams<Context, BotNavigation.Params, (Boolean) -> Unit> {

    @Parcelize
    data class Params(
        val text: String? = null,
    ) : Base.Params, Parcelable

    companion object {
        const val PARAM = "param"
    }

}

class BotNavigationImpl : BotNavigation {
    override fun navigate(
        navigation: Context,
        params: BotNavigation.Params,
        callback: (Boolean) -> Unit,
    ) {
        try {
            navigation.startActivity(
                Intent(
                    navigation,
                    Class.forName("com.ribsky.bot.ui.BotActivity")
                ).apply {
                    putExtra(BotNavigation.PARAM, params)
                }
            )
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }

}
