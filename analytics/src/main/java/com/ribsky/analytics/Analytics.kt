package com.ribsky.analytics

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object Analytics {

    enum class Event(val param: String) {
        SHARE_SMS("share_sms"),
        SHARE_WORDS("share_word"),
        START_LESSON("start_lesson"),
        START_GAME("start_game"),
        START_WORDS("start_words")
    }

    fun logEvent(event: Event) {
        logEventWithFirebase(event.param)
    }

    private fun logEventWithFirebase(event: String) {
        Firebase.analytics.logEvent(event, null)
    }
}
