package com.ribsky.lesson.adapter.chat.factory.type

import com.ribsky.lesson.model.ChatModel

interface TypeFactory {

    fun type(item: ChatModel.Text): Int
    fun type(item: ChatModel.TextFromUser): Int
    fun type(item: ChatModel.TranslateText): Int
    fun type(item: ChatModel.Test): Int
    fun type(item: ChatModel.Mistake): Int
    fun type(item: ChatModel.Chips): Int
    fun type(item: ChatModel.Image): Int
    fun type(item: ChatModel.Answer): Int
}
