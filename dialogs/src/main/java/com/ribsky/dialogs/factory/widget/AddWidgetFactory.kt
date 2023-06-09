package com.ribsky.dialogs.factory.widget

import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class AddWidgetFactory(
    private val positiveButtonCallback: () -> Unit = {},
) : DialogFactory {

    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        title = "Додати віджет \uD83C\uDFE0"
        description = "Додай віджет для швидкого доступу до нових слів та фраз"
        icon {
            icon = commonDrawable.ic_outline_widgets_24
        }
        positiveButton {
            text = "Додати"
            callback = positiveButtonCallback
        }
    }
}
