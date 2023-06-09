package com.ribsky.bot.helper.bot

interface BotHelper {

    fun init(token: String)

    fun getAnswer(
        question: String,
        callback: (answer: String) -> Unit,
        error: (error: String) -> Unit,
    )
}
