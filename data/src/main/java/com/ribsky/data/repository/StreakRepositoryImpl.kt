package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.core.utils.DateUtils.Companion.getCurrentDayInMillis
import com.ribsky.domain.repository.StreakRepository

class StreakRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : StreakRepository {

    override fun isTodayStreak(): Boolean =
        sharedPreferences.getLong(PREF_STREAK_TODAY, 0) == getCurrentDayInMillis()


    override fun getCurrentStreak(): Int {
        if (getCurrentDayInMillis() - sharedPreferences.getLong(PREF_STREAK_TODAY, 0) > 86400000 ||
            getCurrentDayInMillis() - sharedPreferences.getLong(PREF_STREAK_TODAY, 0) < 0
        ) {
            sharedPreferences.edit { putInt(PREF_STREAK, 0) }
        }
        return sharedPreferences.getInt(PREF_STREAK, 0)
    }

    override fun setTodayStreak() {
        if (!isTodayStreak()) {
            sharedPreferences.edit {
                putLong(PREF_STREAK_TODAY, getCurrentDayInMillis())
                putInt(PREF_STREAK, getCurrentStreak() + 1)
            }
        }
    }

    override fun getStreakLastDay(): Long = sharedPreferences.getLong(PREF_STREAK_TODAY, 0)

    override fun setCurrentStreak(streak: Int) {
        sharedPreferences.edit { putInt(PREF_STREAK, streak) }
    }

    override fun setStreakLastDay(streakLastDay: Long) {
        sharedPreferences.edit { putLong(PREF_STREAK_TODAY, streakLastDay) }
    }


    companion object {
        private const val PREF_STREAK = "streak"
        private const val PREF_STREAK_TODAY = "streak_today"
    }


}