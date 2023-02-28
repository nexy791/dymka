package com.ribsky.settings.model

sealed class Settings {

    abstract val id: Int

    enum class Type {
        SHARE, SUPPORT, CONTACT, RATE, UPDATE, LIBRARY, EXIT, DELETE
    }

    data class SettingsModel(
        override val id: Int,
        val title: String,
        val drawable: Int,
        val type: Type,
    ) : Settings()

    data class LabelModel(
        override val id: Int,
        val label: String,
    ) : Settings()
}
