package com.ribsky.game.utils.ext

import java.util.concurrent.TimeUnit

class TimerExt {

    companion object {
        fun formatTime(millisUntilFinished: Long) = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
            )
        )
    }
}
