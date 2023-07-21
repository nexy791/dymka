package com.ribsky.testing.core.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ribsky.core.utils.DateUtils
import io.mockk.mockkObject
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class DateUtilsTest {

    data class Date(
        val pattern: String,
        val date: Long,
        val expected: String?,
    )

    @Before
    fun setup() {
        mockkObject(DateUtils)
    }

    @Test
    fun getCurrentDayInMillis_shouldReturnCurrentDayInMillis() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }
        val expected = calendar.timeInMillis

        val currentDayInMillis = DateUtils.getCurrentDayInMillis()

        assertEquals(expected, currentDayInMillis)

    }

    @Test
    fun formatDateByPattern_withPatternAndDate_shouldReturnFormattedDateOrNullIfDateOrPatternIsInvalid() {

        val list = listOf(
            Date("dd MMMM HH:mm", 1614556800000L, "1 березня 00:00"),
            Date("dd MMMM yyyy", 1614556800000L, "1 березня 2021"),
            Date("mm:ss", 1614556800000L, "00:00"),

            Date("dd MMMM HH:mm", -4, null),
            Date("foo", 1614556800000L, null),
            Date("foo", -4, null),
        )

        list.forEach {
            val formattedDate = DateUtils.formatDateByPattern(it.pattern, it.date)
            assertEquals(it.expected, formattedDate)
        }
    }


}