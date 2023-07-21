package com.ribsky.testing.data.repository

import android.content.SharedPreferences
import com.ribsky.billing.manager.SubManager
import com.ribsky.data.repository.BotRepositoryImpl
import com.ribsky.domain.repository.BotRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class BotRepositoryImplTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var subManager: SubManager
    private lateinit var botRepository: BotRepository

    @Before
    fun setUp() {
        sharedPreferences = mockk()
        subManager = mockk()
        botRepository = BotRepositoryImpl(sharedPreferences, subManager)
    }

    @Test
    fun getTotalAnswers_shouldReturnCorrectValue() {
        every { sharedPreferences.getInt(BotRepositoryImpl.KEY_TOTAL_ANSWERS, 0) } returns 10

        val result = botRepository.getTotalAnswers()

        assert(result == 10)
    }

    @Test
    fun getDailyAnswers_shouldReturnCorrectValue() {
        every { sharedPreferences.getInt(BotRepositoryImpl.KEY_DAILY_ANSWERS, 0) } returns 10

        val result = botRepository.getDailyAnswers()

        assert(result == 10)

    }

    @Test
    fun getAvailableAnswers_shouldReturnCorrectValueForRegularUser() {
        val totalAnswers = 10
        val dailyAnswers = 10
        val isSub = false
        every {
            sharedPreferences.getInt(
                BotRepositoryImpl.KEY_TOTAL_ANSWERS,
                0
            )
        } returns totalAnswers
        every {
            sharedPreferences.getInt(
                BotRepositoryImpl.KEY_DAILY_ANSWERS,
                0
            )
        } returns dailyAnswers
        every { subManager.isSub() } returns isSub

        val result = botRepository.getAvailableAnswers()

        assertEquals(result, BotRepositoryImpl.MAX_REGULAR_ANSWERS - totalAnswers)
    }

    @Test
    fun getAvailableAnswers_shouldReturnCorrectValueForPremiumUser() {
        val totalAnswers = 10
        val dailyAnswers = 10
        val isSub = true
        every {
            sharedPreferences.getInt(
                BotRepositoryImpl.KEY_TOTAL_ANSWERS,
                0
            )
        } returns totalAnswers
        every {
            sharedPreferences.getInt(
                BotRepositoryImpl.KEY_DAILY_ANSWERS,
                0
            )
        } returns dailyAnswers
        every { subManager.isSub() } returns isSub

        val result = botRepository.getAvailableAnswers()

        assertEquals(result, BotRepositoryImpl.MAX_PREMIUM_ANSWERS - dailyAnswers)
    }

    @Test
    fun canBotAnswer_shouldReturnTrueWhenAvailableAnswersMoreThanZeroForRegularUser() {
        val answers = 10
        val isSub = false
        every { botRepository.getAvailableAnswers() } returns answers
        every { subManager.isSub() } returns isSub

        val result = botRepository.canBotAnswer()
        assertEquals(result, true)
    }

    @Test
    fun canBotAnswer_shouldReturnTrueWhenAvailableAnswersMoreThanZeroForPremiumUser() {
        val answers = 10
        val isSub = true
        every { botRepository.getAvailableAnswers() } returns answers
        every { subManager.isSub() } returns isSub

        val result = botRepository.canBotAnswer()
        assertEquals(result, true)
    }

    @Test
    fun canBotAnswer_shouldReturnFalseWhenAvailableAnswersEqualsZeroForRegularUser() {
        val answers = 0
        val isSub = false
        every { botRepository.getAvailableAnswers() } returns answers
        every { subManager.isSub() } returns isSub

        val result = botRepository.canBotAnswer()
        assertEquals(result, false)
    }



}