package com.ribsky.bot.model

import kotlin.random.Random

sealed class ChatModel {

    abstract val id: Int

    class User(val message: String, override val id: Int = Random.nextInt()) : ChatModel()

    class Bot(val message: String, override val id: Int = Random.nextInt()) : ChatModel()

    object Loading : ChatModel() {
        override val id: Int = Random.nextInt()
    }
}
