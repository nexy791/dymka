package com.ribsky.test.model

import com.ribsky.domain.model.word.BaseWordModel

class WordModel(
    override val id: String,
    override val originalWord: String,
    override val question: String,
    override val translatedWords: List<BaseWordModel.PickModel>,
    var isSaved: Boolean,
) : BaseWordModel {
    constructor(id: BaseWordModel) : this(
        id = id.id,
        originalWord = id.originalWord,
        question = id.question,
        translatedWords = id.translatedWords,
        isSaved = false
    )
}
