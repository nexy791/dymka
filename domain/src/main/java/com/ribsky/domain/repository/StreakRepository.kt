package com.ribsky.domain.repository

interface StreakRepository {

    fun isTodayStreak(): Boolean

    fun getCurrentStreak(): Int

    fun setTodayStreak()

    fun getStreakLastDay(): Long

    fun setCurrentStreak(streak: Int)

    fun setStreakLastDay(streakLastDay: Long)

}