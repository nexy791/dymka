package com.ribsky.data.service.online.paragraph

import com.ribsky.data.model.ParagraphApiModel

interface ParagraphService {

    suspend fun loadParagraphs(): Result<List<ParagraphApiModel>>
}
