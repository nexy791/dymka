package com.ribsky.dialogs.factory.bot

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class BotInfoFactory(
    private val positiveButtonCallback: () -> Unit = {},
) : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Інформація ℹ️"
        descriptionSize = 11f
        description =
            "Цей сервіс може містити переклади та відповіді, що працюють на технологіях Google (ML Kit Translation, ML Kit Smart Reply) та OpenAI (API). Будь ласка, пам'ятай, що Google, OpenAI та розробники відмовляються від усіх гарантій, пов'язаних з перекладами та відповідями, будь то явних або припущених, включаючи будь-які гарантії точності, надійності та будь-які припущені гарантії товарної придатності, відповідності певній меті та невідповідності правам третіх сторін. Цей сервіс створений для допомоги у вивченні української мови, але він не гарантує 100% правильних відповідей, і його використання залежить від твоєї власної відповідальності."
        icon {
            icon = commonDrawable.ic_outline_info_24
        }
        positiveButton {
            text = "Підтримка"
            callback = positiveButtonCallback
        }
        negativeButton {
            text = "Закрити"
        }
    }
}
