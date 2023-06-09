package com.ribsky.data.service.offline.time

import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.*

class TimeServiceImpl(
    private val sharedPreferences: SharedPreferences,
) : TimeService {

    override fun isNeedUpdate(): Boolean {
        val lastTimeUpdate = getLastTimeUpdate()
        if (lastTimeUpdate <= 0L) return true
        val currentTime = Date().time
        return currentTime - lastTimeUpdate > 21600000 || currentTime < lastTimeUpdate
    }

    override fun saveLastTimeUpdate() {
        sharedPreferences.edit {
            putLong(KEY_LAST_UPDATE, Date().time)
        }
    }

    override fun getLastTimeUpdate(): Long = sharedPreferences.getLong(KEY_LAST_UPDATE, 0)

    private companion object {
        private const val KEY_LAST_UPDATE = "last_update"
    }
}
