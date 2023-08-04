package com.ribsky.dialogs.factory.stars

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class StarsFactory(
    private val stars: Int,
    private val starsAll: Int,
    private val positiveButtonCallback: () -> Unit = {},
    private val negativeButtonCallback: () -> Unit = {},
) : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "${stars}/${starsAll} зірок ⭐"
        description =
            "Проходь уроки без помилок, щоб отримати зірочки та відкрити нові теми, або розблокуй усі теми з Преміум"
        icon {
            icon = commonDrawable.ic_round_star_24
        }
        positiveButton {
            text = "Добре"
            callback = positiveButtonCallback
        }
        negativeButton {
            text = "Преміум"
            callback = negativeButtonCallback
        }
    }
}
