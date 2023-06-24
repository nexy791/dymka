package com.ribsky.data.repository

import android.content.SharedPreferences
import android.os.SystemClock
import com.ribsky.billing.manager.SubManager
import com.ribsky.domain.repository.BotRepository

class BotRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val subManager: SubManager,
) : BotRepository {

    private var totalAnswers: Int = 0
    private var dailyAnswers: Int = 0
    private var lastUpdateDayOfYear: Long = 0

    init {
        loadDataFromSharedPreferences()
        checkAndUpdateDataForNewDay()
    }

    override fun getTotalAnswers(): Int = totalAnswers

    override fun getDailyAnswers(): Int = dailyAnswers

    override fun getAvailableAnswers(): Int {
        return if (subManager.isSub()) {
            MAX_PREMIUM_ANSWERS - dailyAnswers
        } else {
            MAX_REGULAR_ANSWERS - totalAnswers
        }
    }

    override fun incrementAnswers() {
        totalAnswers++
        if (subManager.isSub()) {
            dailyAnswers++
        }
        saveDataToSharedPreferences()
    }

    override fun setDefaultTotalAnswers(defaultTotalAnswers: Int) {
        totalAnswers = defaultTotalAnswers
        saveDataToSharedPreferences()
    }

    override fun setDefaultDailyAnswers(defaultDailyAnswers: Int) {
        dailyAnswers = defaultDailyAnswers
        saveDataToSharedPreferences()
    }

    override fun canBotAnswer(): Boolean {
        val availableAnswers = getAvailableAnswers()
        return availableAnswers > 0
    }

    private fun loadDataFromSharedPreferences() {
        totalAnswers = sharedPreferences.getInt(KEY_TOTAL_ANSWERS, 0)
        dailyAnswers = sharedPreferences.getInt(KEY_DAILY_ANSWERS, 0)
        lastUpdateDayOfYear =
            sharedPreferences.getLong(KEY_LAST_UPDATE_DAY, SystemClock.elapsedRealtime())
    }

    private fun saveDataToSharedPreferences() {
        sharedPreferences.edit().apply {
            putInt(KEY_TOTAL_ANSWERS, totalAnswers)
            putInt(KEY_DAILY_ANSWERS, dailyAnswers)
            if (dailyAnswers <= 1) {
                putLong(KEY_LAST_UPDATE_DAY, lastUpdateDayOfYear)
            }
            apply()
        }
    }

    private fun checkAndUpdateDataForNewDay() {
        val currentDate = SystemClock.elapsedRealtime()
        if (currentDate - lastUpdateDayOfYear >= 24 * 60 * 60 * 1000 || currentDate - lastUpdateDayOfYear < 0) {
            dailyAnswers = 0
            lastUpdateDayOfYear = currentDate
            saveDataToSharedPreferences()
        }
    }

    companion object {
        private const val MAX_REGULAR_ANSWERS = 20
        private const val MAX_PREMIUM_ANSWERS = 50

        private const val KEY_TOTAL_ANSWERS = "total_answers"
        private const val KEY_DAILY_ANSWERS = "daily_answers"
        private const val KEY_LAST_UPDATE_DAY = "last_update_day"
    }
}
