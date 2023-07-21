package com.ribsky.dialogs.factory.cats

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class CatsFactory : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Наші котики! \uD83D\uDE3B"
        description =
            "Завдяки нашим підписникам dymka постійно стає краще. Дякуємо вам!"
        icon {
            icon = commonDrawable.ic_round_pets_24
        }
        positiveButton {
            text = "Продовжити"
        }
    }

}
