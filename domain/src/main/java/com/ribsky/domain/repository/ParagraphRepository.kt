package com.ribsky.domain.repository

import com.ribsky.domain.model.paragraph.BaseParagraphModel

interface ParagraphRepository {

    suspend fun loadParagraphs(): Result<List<BaseParagraphModel>>

    suspend fun getParagraphs(): List<BaseParagraphModel>

    suspend fun getParagraph(id: String): BaseParagraphModel

    suspend fun isNotEmpty(): Boolean
}
