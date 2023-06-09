package com.ribsky.account.utils.ext

import android.app.Activity
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.AlertsExt.Companion.showAlert

class AlertExt {

    companion object {
        fun Activity.flagUser(name: String) {
            showAlert(
                title = "Поскаржитись",
                message = "Ти впевнений, що хочеш поскаржитись на цього користувача?",
                positiveButton = "Надіслати скаргу",
                positiveAction = {
                    sendEmail(
                        subject = "dymka скарга на користувача",
                        text = "Користувач: $name\n\n"
                    )
                },
                negativeButton = "Скасувати",
                negativeAction = {}
            )
        }
    }
}
