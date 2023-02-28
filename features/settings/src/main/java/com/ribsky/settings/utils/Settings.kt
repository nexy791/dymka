package com.ribsky.settings.utils

import com.ribsky.common.alias.commonDrawable
import com.ribsky.settings.model.Settings

object Settings {

    val list = listOf(
        Settings.LabelModel(0, "Підтримка"),
        Settings.SettingsModel(
            1,
            "Розповісти",
            commonDrawable.ic_outline_share_24,
            Settings.Type.SHARE
        ),
        Settings.SettingsModel(
            2,
            "Підтримати",
            commonDrawable.ic_outline_payments_24,
            Settings.Type.SUPPORT
        ),
        Settings.SettingsModel(
            3,
            "Написати нам",
            commonDrawable.ic_outline_email_24,
            Settings.Type.CONTACT
        ),
        Settings.LabelModel(0, "Застосунок"),
        Settings.SettingsModel(
            4,
            "Оцінити",
            commonDrawable.ic_outline_star_rate_24,
            Settings.Type.RATE
        ),
        Settings.SettingsModel(
            5,
            "Оновити",
            commonDrawable.ic_round_update_24,
            Settings.Type.UPDATE
        ),
        Settings.SettingsModel(
            6,
            "Ліцензії",
            commonDrawable.ic_outline_local_library_24,
            Settings.Type.LIBRARY
        ),
        Settings.LabelModel(5, "Акаунт"),
        Settings.SettingsModel(
            7,
            "Вийти з акаунту",
            commonDrawable.ic_round_exit_to_app_24,
            Settings.Type.EXIT
        ),
        Settings.SettingsModel(
            8,
            "Видалити акаунт",
            commonDrawable.ic_outline_delete_24,
            Settings.Type.DELETE
        ),
    )
}
