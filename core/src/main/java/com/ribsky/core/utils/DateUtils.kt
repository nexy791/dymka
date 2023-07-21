package com.ribsky.core.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateUtils {

    companion object {

        fun getCurrentDayInMillis(): Long = Calendar.getInstance().apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
        }.timeInMillis

        fun formatDateDDMMMMHHMM(date: Long): String =
            formatDateByPattern("dd MMMM HH:mm", date) ?: "Невизначено"

        fun formatDateDDMMMMYYYY(date: Long): String? = formatDateByPattern("dd MMMM yyyy", date)

        fun formatDateMMSS(date: Long): String = formatDateByPattern("mm:ss", date) ?: "Невизначено"


        fun formatDateByPattern(pattern: String, date: Long): String? = runCatching {
            val formatter = SimpleDateFormat(pattern, Locale("uk"))
            formatter.format(date)
        }.getOrNull()


    }

}