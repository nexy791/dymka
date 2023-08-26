package com.ribsky.dialogs.factory.notification

import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.dialogs.base.DialogFactory
import com.ribsky.dialogs.base.SimpleDialog
import com.ribsky.dialogs.base.SimpleDialog.Companion.icon
import com.ribsky.dialogs.base.SimpleDialog.Companion.negativeButton
import com.ribsky.dialogs.base.SimpleDialog.Companion.positiveButton

class NotificationPermissionFactory(
    private var onConfirm: () -> Unit = {},
) : DialogFactory {


    override fun createDialog(): SimpleDialog = SimpleDialog.create {
        Analytics.logEvent(Analytics.Event.NOTIFICATION_DIALOG_OPEN)
        title = "Немає сповіщень \uD83D\uDE36"
        description = "Щоб тобі приходили сповіщення, потрібно дозволити їх в налаштуваннях"
        icon {
            icon = commonDrawable.ic_outline_notification_add_24
        }
        positiveButton {
            text = "Добре"
            callback = this@NotificationPermissionFactory.onConfirm
        }
        negativeButton {
            text = "Ні, дякую"
        }
    }

}
