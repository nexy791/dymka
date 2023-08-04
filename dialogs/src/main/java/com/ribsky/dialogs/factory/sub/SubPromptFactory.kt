package com.ribsky.dialogs.factory.sub

import android.graphics.Color
import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class SubPromptFactory(
    private val positiveButtonCallback: () -> Unit = {},
) : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Преміум контент \uD83C\uDF1F"
        description = "Щоб продовжити, необхідно\nмати Преміум-акаунт"
        imageColor = Color.parseColor("#2c3377")
        icon {
            icon = commonDrawable.ic_round_hotel_class_24
            color = Color.parseColor("#f0be45")
        }
        positiveButton {
            text = "Продовжити"
            callback = positiveButtonCallback
            backgroundColor = Color.parseColor("#2c3377")
            textColor = Color.parseColor("#ffffff")
        }
    }
}
