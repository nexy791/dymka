package com.ribsky.testing.analytics

import androidx.core.os.bundleOf
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ribsky.analytics.Analytics
import com.ribsky.analytics.AnalyticsHelper
import io.mockk.every
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AnalyticsTest {

    @Before
    fun setup() {
        mockkObject(Analytics)
    }

    @Test
    fun logEvent_withEventAndBundle_inDebugMode_shouldCallLogEventWithLogcat() {
        mockkObject(Analytics)
        mockkObject(AnalyticsHelper)

        val event = Analytics.Event.END_LESSON
        val bundle = bundleOf(
            "id" to "1"
        )

        every { AnalyticsHelper.isDebug() } returns true

        Analytics.logEvent(event, bundle)

        assertEquals(
            "Analytics: name: ${event.param}, bundle: $bundle",
            getLogcatMessage()
        )

    }

    private fun getLogcatMessage(): String? {
        return try {
            val process = Runtime.getRuntime().exec("logcat -d")
            val bufferedReader = process.inputStream.bufferedReader()
            val log = bufferedReader.use { it.readText() }
            "Analytics: name: " + log.substringAfter("Analytics: name: ").substringBefore("\n")
        } catch (e: Exception) {
            null
        }
    }


}