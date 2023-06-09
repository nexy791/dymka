package com.ribsky.bot.helper.translator

interface TranslatorHelper {

    enum class Language(val code: String) {
        UKRAINIAN("uk"),
        ENGLISH("en"),
    }

    fun init()

    suspend fun translate(text: String, language: Language): Result<String>
}
