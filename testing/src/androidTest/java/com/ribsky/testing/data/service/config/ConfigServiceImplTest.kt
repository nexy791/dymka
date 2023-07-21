package com.ribsky.testing.data.service.config

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ribsky.data.service.config.ConfigServiceImpl
import com.ribsky.testing.mock.TestTask
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class ConfigServiceImplTest {

    private val remoteConfig: FirebaseRemoteConfig = mockk()
    private lateinit var configService: ConfigServiceImpl

    private val currentDate get() = System.currentTimeMillis()

    @Before
    fun setUp() {
        configService = ConfigServiceImpl(remoteConfig)
    }

    @Test
    fun getBotToken_shouldReturnSuccessWhenTokenIsNotEmpty() = runTest {
        val token = "token"


        every { remoteConfig.fetchAndActivate() } returns TestTask(true)
        every { remoteConfig.getLong(ConfigServiceImpl.KEY_DISCOUNT) } returns currentDate
        every { remoteConfig.getString(ConfigServiceImpl.KEY_BOT) } returns token

        val result = configService.getBotToken()

        assert(result.isSuccess)
        assertEquals(result.getOrNull(), token)
    }

    @Test
    fun getBotToken_shouldReturnFailureWhenTokenIsEmpty() = runTest {
        val token = ""

        every { remoteConfig.fetchAndActivate() } returns TestTask(true)
        every { remoteConfig.getLong(ConfigServiceImpl.KEY_DISCOUNT) } returns currentDate
        every { remoteConfig.getString(ConfigServiceImpl.KEY_BOT) } returns token

        val result = configService.getBotToken()

        assert(result.isFailure)
    }

    @Test
    fun getDiscount_shouldReturnSuccessWhenDiscountIsNotExpired() = runTest {
        val tomorrow = System.currentTimeMillis() + 24 * 60 * 60 * 1000
        val token = "token"

        every { remoteConfig.fetchAndActivate() } returns TestTask(true)

        every { remoteConfig.getLong(ConfigServiceImpl.KEY_DISCOUNT) } returns tomorrow
        every { remoteConfig.getString(ConfigServiceImpl.KEY_BOT) } returns token

        val result = configService.getDiscount()

        assert(result.isSuccess)
        assertEquals(result.getOrNull(), tomorrow.toString())
    }

    @Test
    fun getDiscount_shouldReturnFailureWhenDiscountIsExpired() = runTest {
        val yesterday = System.currentTimeMillis() - 24 * 60 * 60 * 1000
        val token = "token"

        every { remoteConfig.fetchAndActivate() } returns TestTask(true)

        every { remoteConfig.getLong(ConfigServiceImpl.KEY_DISCOUNT) } returns yesterday
        every { remoteConfig.getString(ConfigServiceImpl.KEY_BOT) } returns token

        val result = configService.getDiscount()

        assert(result.isFailure)
    }

    @Test
    fun getDiscountOrBot_shouldReturnFailureWhenActiveIsFalse() = runTest {
        val token = "token"
        val tommorow = System.currentTimeMillis() + 24 * 60 * 60 * 1000

        every { remoteConfig.fetchAndActivate() } returns TestTask(false)
        every { remoteConfig.getLong(ConfigServiceImpl.KEY_DISCOUNT) } returns tommorow
        every { remoteConfig.getString(ConfigServiceImpl.KEY_BOT) } returns token

        val resultDiscount = configService.getDiscount()
        val resultBot = configService.getBotToken()

        assert(resultDiscount.isFailure)
        assert(resultBot.isFailure)

    }


}