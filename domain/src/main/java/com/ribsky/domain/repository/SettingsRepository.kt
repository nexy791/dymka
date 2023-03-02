package com.ribsky.domain.repository

interface SettingsRepository {

    val isShouldShowRateDialog: Boolean

    val lastTimeUpdate: Long
}
