package com.ribsky.analytics

import android.os.Bundle
import android.os.Parcelable
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.ribsky.analytics.AnalyticsHelper.Companion.isDebug
import kotlinx.parcelize.Parcelize

object Analytics {

    @Parcelize
    sealed class Event(val param: String) : Parcelable {
        object SHARE_SMS : Event("share_sms")
        object SHARE_WORDS : Event("share_words")
        object SHARE_STORY : Event("share_story")
        object SHARE_STREAK : Event("share_streak")

        object START_LESSON : Event("start_lesson")
        object START_GAME : Event("start_game")
        object START_WORDS : Event("start_words")

        object END_LESSON : Event("end_lesson")

        object END_TEST : Event("end_test")

        object END_LESSON_UNFINISHED : Event("end_lesson_unfinished")

        object END_GAME : Event("end_game")
        object END_WORDS : Event("end_words")

        object PAYWALL_OPEN : Event("paywall_open")

        object BOT_OPEN : Event("bot_open")
        object BOT_OPEN_FROM_LESSON : Event("bot_open_from_lesson")
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
        object PREMIUM_FROM_SETTINGS : Event("premium_from_settings")
        object PREMIUM_FROM_PAYWALL : Event("premium_from_paywall")
        object PREMIUM_FROM_STARS : Event("premium_from_stars")

        object PREMIUM_FROM_NOTES : Event("premium_from_notes")

        object PREMIUM_BUY_FROM_LESSON : Event("premium_buy_from_lesson")
        object PREMIUM_BUY_FROM_WORDS : Event("premium_buy_from_words")
        object PREMIUM_BUY_FROM_LIKE : Event("premium_buy_from_like")
        object PREMIUM_BUY_FROM_GAME : Event("premium_buy_from_game")
        object PREMIUM_BUY_FROM_BOT : Event("premium_buy_from_bot")
        object PREMIUM_BUY_FROM_MAIN : Event("premium_buy_from_main")
        object PREMIUM_BUY_FROM_USER : Event("premium_buy_from_user")
        object PREMIUM_BUY_FROM_HINT : Event("premium_buy_from_hint")
        object PREMIUM_BUY_FROM_MENU : Event("premium_buy_from_menu")
        object PREMIUM_BUY_FROM_SETTINGS : Event("premium_buy_from_settings")
        object PREMIUM_BUY_FROM_PAYWALL : Event("premium_buy_from_paywall")
        object PREMIUM_BUY_FROM_STARS : Event("premium_buy_from_stars")

        object PREMIUM_BUY_FROM_NOTES : Event("premium_buy_from_notes")

        object PREMIUM_BUY_FROM_UNKNOWN : Event("premium_buy_from_unknown")

        object LESSON_ANSWER_CORRECT : Event("lesson_answer_correct")
        object LESSON_ANSWER_INCORRECT : Event("lesson_answer_incorrect")

        object LESSON_HINT_CLICK : Event("lesson_hint_click")

        object WORDS_ANSWER_CORRECT : Event("words_answer_correct")
        object WORDS_ANSWER_INCORRECT : Event("words_answer_incorrect")

        object INTRO_OPEN : Event("intro_open")
        object INTRO_DONE : Event("intro_close")

        object INTRO_GOAL_PICK : Event("intro_goal_pick")

        object INTRO_LEVEL_PICK : Event("intro_level_pick")

        object INTRO_FROM_PICK : Event("intro_from_pick")

        object STREAK_OPEN : Event("streak_open")

        object STREAK_DONE : Event("streak_done")

        class STARS_COLLECTED(val stars: Int) : Event("stars_collected_$stars")

        object TOP_DOWN_STARS : Event("top_down_stars")

        object TOP_DOWN_TESTS : Event("top_down_tests")

        object TOP_UP_STARS : Event("top_up_stars")

        object TOP_UP_TESTS : Event("top_up_tests")

        object TOP_MORE_STARS : Event("top_more_stars")

        object TOP_MORE_TESTS : Event("top_more_tests")

        object NOTES_OPEN : Event("notes_open")

        object NOTES_ADD : Event("notes_add")

        object NOTES_LIMIT : Event("notes_limit")

        object OPEN_AUTH : Event("open_auth")

        object PASS_AUTH : Event("pass_auth")

        object ERROR : Event("error")


    }

    fun logEvent(event: Event, bundle: Bundle? = null) {
        when (isDebug()) {
            true -> logEventWithLogcat(event, bundle)
            false -> logEventWithFirebase(event, bundle)
        }
    }

    private fun logEventWithFirebase(event: Event, bundle: Bundle? = null) {
        Firebase.analytics.logEvent(event.param, bundle)
    }

    private fun logEventWithLogcat(event: Event, bundle: Bundle? = null) {
        println("Analytics: name: ${event.param}, bundle: $bundle")
    }

}
