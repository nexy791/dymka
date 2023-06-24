package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.domain.repository.StreakRepository
import java.util.*

class StreakRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : StreakRepository {

    override fun isTodayStreak(): Boolean =
        sharedPreferences.getLong(PREF_STREAK_TODAY, 0) == currentDay


    override fun getCurrentStreak(): Int {
        if (currentDay - sharedPreferences.getLong(PREF_STREAK_TODAY, 0) > 86400000 ||
            currentDay - sharedPreferences.getLong(PREF_STREAK_TODAY, 0) < 0
        ) {
            sharedPreferences.edit { putInt(PREF_STREAK, 0) }
        }
        return sharedPreferences.getInt(PREF_STREAK, 0)
    }

    override fun setTodayStreak() {
        if (!isTodayStreak()) {
            sharedPreferences.edit {
                putLong(PREF_STREAK_TODAY, currentDay)
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

    private val currentDay
        get() = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }.timeInMillis


    companion object {
        private const val PREF_STREAK = "streak"
        private const val PREF_STREAK_TODAY = "streak_today"
    }


}