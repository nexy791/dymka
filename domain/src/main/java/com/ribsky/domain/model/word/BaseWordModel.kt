package com.ribsky.domain.model.word

interface BaseWordModel {
    val id: String
    val originalWord: String
    val question: String
    val translatedWords: List<PickModel>

    interface PickModel {
        val name: String
        val correct: Boolean
        var isShown: Boolean
    }
}
