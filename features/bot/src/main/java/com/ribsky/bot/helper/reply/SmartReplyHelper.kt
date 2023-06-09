package com.ribsky.bot.helper.reply

interface SmartReplyHelper {

    fun init()

    fun addMessage(message: String, isFromUser: Boolean)

    suspend fun getReplies(): Result<List<String>>
}
