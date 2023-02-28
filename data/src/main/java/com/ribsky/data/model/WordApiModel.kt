package com.ribsky.data.model

import com.ribsky.domain.model.word.BaseWordModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WordApiModel(
    @Json(name = "id") override val id: String,
    @Json(name = "original_word") override val originalWord: String,
    @Json(name = "question") override val question: String,
    @Json(name = "translated_word") override val translatedWords: List<PickApiModel>,
) : BaseWordModel {

    class PickApiModel(
        override val name: String,
        override val correct: Boolean,
        override var isShown: Boolean = false,
    ) : BaseWordModel.PickModel
}
