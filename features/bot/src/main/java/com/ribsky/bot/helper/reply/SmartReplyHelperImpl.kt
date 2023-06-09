package com.ribsky.bot.helper.reply

import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplyGenerator
import com.google.mlkit.nl.smartreply.TextMessage
import kotlinx.coroutines.tasks.await

class SmartReplyHelperImpl : SmartReplyHelper {

    private val conversation: MutableList<TextMessage> = mutableListOf()
    private var smartReply: SmartReplyGenerator? = null

    override fun init() {
        smartReply = SmartReply.getClient()
    }

    override fun addMessage(message: String, isFromUser: Boolean) {
        if (isFromUser) {
            conversation.add(
                TextMessage.createForLocalUser(
                    message,
                    System.currentTimeMillis()
                )
            )
        } else {
            conversation.add(
                TextMessage.createForRemoteUser(
                    message,
                    System.currentTimeMillis(),
                    "1"
                )
            )
        }
    }

    override suspend fun getReplies(): Result<List<String>> {
        return runCatching {
            smartReply!!.suggestReplies(conversation).await()?.suggestions?.map { it.text }.also {
            }
                ?: throw Exception("SmartReply is null")
        }
    }
}
