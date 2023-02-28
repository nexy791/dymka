package com.ribsky.lesson.adapter.chat.factory.type

import com.ribsky.lesson.R
import com.ribsky.lesson.model.ChatModel

class TypeFactoryImpl : TypeFactory {

    override fun type(item: ChatModel.Text): Int = R.layout.item_chat_text

    override fun type(item: ChatModel.TextFromUser): Int = R.layout.item_chat_text_spending

    override fun type(item: ChatModel.TranslateText): Int = R.layout.item_chat_translate_text

    override fun type(item: ChatModel.Test): Int = R.layout.item_chat_test

    override fun type(item: ChatModel.Mistake): Int = R.layout.item_chat_mistake

    override fun type(item: ChatModel.Chips): Int = R.layout.item_chat_chips

    override fun type(item: ChatModel.Image): Int = R.layout.item_chat_image

    override fun type(item: ChatModel.Answer): Int = R.layout.item_chat_answer
}
