package com.ribsky.data.service.offline.time

import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.Date

class TimeServiceImpl(
    private val sharedPreferences: SharedPreferences,
) : TimeService {

    // 4 hours
    override fun isNeedUpdate(): Boolean {
        val lastTimeUpdate = getLastTimeUpdate()
        if (lastTimeUpdate <= 0L) return true
        val currentTime = Date().time
        return currentTime - lastTimeUpdate > 14400000 || currentTime < lastTimeUpdate
    }

    override fun saveLastTimeUpdate() {
        sharedPreferences.edit {
            putLong(KEY_LAST_UPDATE, Date().time)
        }
    }

    override fun getLastTimeUpdate(): Long = sharedPreferences.getLong(KEY_LAST_UPDATE, 0)

    private companion object {
        private const val KEY_VERSION = "1.0.1"
        private const val KEY_LAST_UPDATE = "last_update_$KEY_VERSION"
    }
}
