package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.data.service.offline.time.TimeService
import com.ribsky.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val timeService: TimeService,
) : SettingsRepository {

    override val isShouldShowRateDialog: Boolean
        get() = calculateRateDialog()

    override val lastTimeUpdate: Long
        get() = timeService.getLastTimeUpdate()

    private fun calculateRateDialog(): Boolean {
        val currentCount = sharedPreferences.getInt(KEY_RATE_DIALOG, 0)
        if (currentCount == 4) return false
        return (currentCount == 3).also {
            sharedPreferences.edit {
                putInt(KEY_RATE_DIALOG, currentCount + 1)
            }
        }
    }

    companion object {
        private const val KEY_RATE_DIALOG = "KEY_RATE_DIALOG"
    }
}
