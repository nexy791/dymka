package com.ribsky.bot.helper.bot

import com.mardillu.openai.OpenAiInitializer
import com.mardillu.openai.model.Message
import com.mardillu.openai.network.OpenApiClient

class BotHelperImpl : BotHelper {

    private var client: OpenApiClient? = null
    private var history = mutableListOf(configMessage)

    override fun init(token: String) {
        OpenAiInitializer.initialize(token)
        client = OpenApiClient()
    }

    override fun getAnswer(
        question: String,
        callback: (answer: String) -> Unit,
        error: (error: String) -> Unit,
    ) {
        val userMessage = Message("user", question)
        client?.getChatCompletion(
            history + userMessage,
            model = "gpt-3.5-turbo",
        ) { result, error ->
            if (error != null) {
                error("$errorMessage<br><br>Помилка: ${error.message}")
            } else {
                val answer = result?.choices?.getOrNull(0)?.message?.content
                if (answer != null) {
                    history.clear()
                    history.add(configMessage)
                    history.add(userMessage)
                    history.add(Message("assistant", answer))
                    callback(answer)
                } else {
                    error(errorMessage)
                }
            }
        }
    }

    companion object {
        private const val errorMessage =
            "\uD83D\uDE05 Схоже я не зможу відповисти на твоє питання, спробуй перефразувати його або звернись до вчителя"

        val configMessage = Message(
            "user",
            "Ти смішний веселий кіт, мій друг, тебе звати Думка, помічник в вивченні української мови. Відповідай тільки українською та тільки дружелюбно, багато шуткуй, говори неформально, звертайся на 'ти'. Відповідай тільки на запитання які стосуються теми української мови, або на суміжні. Використовуй емодзі, щоб текст читався легше та його не було багато. Формуй текст до 150 символів"
        )
    }
}
