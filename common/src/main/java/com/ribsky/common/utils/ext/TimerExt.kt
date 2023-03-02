package com.ribsky.common.utils.ext

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimerExt {

    companion object {
        fun formatTimeUntilFinished(millisUntilFinished: Long) = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
            )
        )

        fun formatTimeDDMMMMHHMM(time: Long): String {
            return try {
                val formatter = SimpleDateFormat("dd MMMM HH:mm", Locale("uk"))
                formatter.format(time)
            } catch (e: Exception) {
                "Невизначено"
            }
        }
    }
}
