package com.ribsky.lesson.model

import com.ribsky.lesson.adapter.chat.factory.type.TypeFactory
import kotlin.random.Random

sealed class ChatModel : BaseItem {

    class Text(
        val text: String,
        override val id: Int = Random.nextInt(),
    ) : ChatModel() {
        override fun type(typeFactory: TypeFactory): Int = typeFactory.type(this)
    }

    class TextFromUser(
        val text: String,
        override val id: Int = Random.nextInt(),
    ) : ChatModel() {
        override fun type(typeFactory: TypeFactory): Int = typeFactory.type(this)
    }

    class TranslateText(
        val text: String,
        override val id: Int = Random.nextInt(),
    ) : ChatModel() {
        override fun type(typeFactory: TypeFactory): Int = typeFactory.type(this)
    }

    class Chips(
        val text: String,
        val chips: List<String>,
        override var isActive: Boolean = true,
        override val id: Int = Random.nextInt(),
    ) :
        ChatModel(), BaseActiveItem {
        override fun type(typeFactory: TypeFactory): Int = typeFactory.type(this)
    }

    class Test(
        val text: String,
        val testModel: List<TestModel>,
        override var isActive: Boolean = true,
        override val id: Int = Random.nextInt(),
    ) :
        ChatModel(), BaseActiveItem {
        class TestModel(val text: String, val isTrue: Boolean)

        override fun type(typeFactory: TypeFactory): Int = typeFactory.type(this)
    }

    class Mistake(
        val text: String,
        override var isActive: Boolean = true,
        override val id: Int = Random.nextInt(),
    ) :
        ChatModel(), BaseActiveItem {
        override fun type(typeFactory: TypeFactory): Int = typeFactory.type(this)
    }

    class Image(val url: String, override val id: Int = Random.nextInt()) : ChatModel() {
        override fun type(typeFactory: TypeFactory): Int = typeFactory.type(this)
    }

    class Answer(
        val text: String,
        override val id: Int = Random.nextInt(),
        val isCorrect: Boolean,
    ) : ChatModel() {
        override fun type(typeFactory: TypeFactory): Int = typeFactory.type(this)
    }
}
