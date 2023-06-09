package com.ribsky.analytics

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object Analytics {

    sealed class Event(val param: String) {
        object SHARE_SMS : Event("share_sms")
        object SHARE_WORDS : Event("share_words")
        object SHARE_STORY : Event("share_story")

        object START_LESSON : Event("start_lesson")
        object START_GAME : Event("start_game")
        object START_WORDS : Event("start_words")

        object END_LESSON : Event("end_lesson")

        object END_LESSON_UNFINISHED : Event("end_lesson_unfinished")

        object END_GAME : Event("end_game")
        object END_WORDS : Event("end_words")

        object BOT_OPEN : Event("bot_open")
        object BOT_LIMIT_PREM : Event("bot_limit_prem")
        object BOT_LIMIT_DEFAULT : Event("bot_limit_default")
        object BOT_HINT_CLICK : Event("bot_hint_click")
        object BOT_ANSWER : Event("bot_answer")
        object BOT_QUESTION : Event("bot_question")
        object BOT_ANSWER_ERROR : Event("bot_answer_error")
        object BOT_DOWNLOAD_ERROR : Event("bot_download_error")

        object BOT_DOWNLOAD_SUCCESS : Event("bot_download_success")

        object BOT_REPLY_ERROR : Event("bot_reply_error")

        object PREMIUM_OPEN : Event("premium_open")
        object PREMIUM_FROM_LESSON : Event("premium_from_lesson")
        object PREMIUM_FROM_WORDS : Event("premium_from_words")
        object PREMIUM_FROM_LIKE : Event("premium_from_like")
        object PREMIUM_FROM_GAME : Event("premium_from_game")
        object PREMIUM_FROM_BOT : Event("premium_from_bot")
        object PREMIUM_FROM_MAIN : Event("premium_from_main")
        object PREMIUM_FROM_USER : Event("premium_from_user")
        object PREMIUM_FROM_HINT : Event("premium_from_hint")
        object PREMIUM_FROM_MENU : Event("premium_from_menu")

        object LESSON_ANSWER_CORRECT : Event("lesson_answer_correct")
        object LESSON_ANSWER_INCORRECT : Event("lesson_answer_incorrect")

        object LESSON_HINT_CLICK : Event("lesson_hint_click")

        object WORDS_ANSWER_CORRECT : Event("words_answer_correct")
        object WORDS_ANSWER_INCORRECT : Event("words_answer_incorrect")


        object ERROR : Event("error")


    }

    fun logEvent(event: Event, bundle: Bundle? = null) {
        logEventWithFirebase(event.param, bundle)
    }

    private fun logEventWithFirebase(event: String, bundle: Bundle? = null) {
        Firebase.analytics.logEvent(event, bundle)
    }
}
