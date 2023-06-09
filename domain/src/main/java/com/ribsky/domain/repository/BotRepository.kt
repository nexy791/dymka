package com.ribsky.domain.repository

interface BotRepository {
    fun getTotalAnswers(): Int
    fun getDailyAnswers(): Int
    fun getAvailableAnswers(): Int
    fun incrementAnswers()
    fun setDefaultTotalAnswers(defaultTotalAnswers: Int)
    fun setDefaultDailyAnswers(defaultDailyAnswers: Int)
    fun canBotAnswer(): Boolean
}
