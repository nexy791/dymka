package com.ribsky.dialogs.factory.limit

import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.utils.ext.ViewExt.Companion.formatStars
import com.ribsky.common.utils.ext.ViewExt.Companion.formatStarsV2
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class LimitFactory(
    private var stars: Int = 0,
    private var needStars: Int = 0,
    private var onConfirm: () -> Unit = {},
    private var onDismiss: () -> Unit = {},
) : DialogFactory {


    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        val starsCount = needStars - stars
        title = "$stars/$needStars зірок ⭐"
        description =
            "Набери ще ${starsCount.formatStarsV2()} в минулій темі, щоб отримати доступ до нової! Або розблокуй усі теми з Преміум"
        icon {
            icon = commonDrawable.ic_round_star_24
        }
        positiveButton {
            text = "Добре"
            callback = this@LimitFactory.onConfirm
        }
        negativeButton {
            text = "Преміум"
            callback = this@LimitFactory.onDismiss
        }
    }

}
