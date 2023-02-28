package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.domain.repository.SharedRepository
import java.text.SimpleDateFormat
import java.util.*

class SharedRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : SharedRepository {

    override val isShouldShowRateDialog: Boolean
        get() = calculateRateDialog()

    override val lastTimeUpdate: String
        get() = calculateLastTime()

    private fun calculateLastTime(): String {
        return try {
            val lastTime = sharedPreferences.getLong(DataRepositoryImpl.KEY_LAST_UPDATE, 0)
            val formatter = SimpleDateFormat("dd MMMM HH:ss", Locale("uk"))
            val mDate = formatter.format(lastTime)
            mDate
        } catch (e: Exception) {
            "Невизначено"
        }
    }

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
