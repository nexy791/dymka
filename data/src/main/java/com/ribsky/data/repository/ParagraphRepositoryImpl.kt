package com.ribsky.data.repository

import com.ribsky.data.mapper.paragraph.ParagraphMapper
import com.ribsky.data.service.offline.paragraph.ParagraphDao
import com.ribsky.data.service.online.paragraph.ParagraphService
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.domain.repository.ParagraphRepository

class ParagraphRepositoryImpl(
    private val paragraphService: ParagraphService,
    private val dao: ParagraphDao,
    private val mapper: ParagraphMapper,
) : ParagraphRepository {

    override suspend fun loadParagraphs(): Result<List<BaseParagraphModel>> {
        val paragraphResult = paragraphService.loadParagraphs()
        paragraphResult.onSuccess {
            dao.insert(it)
        }
        return paragraphResult.map { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getParagraphs(): List<BaseParagraphModel> =
        dao.get().map { mapper.map(it) }

    override suspend fun getParagraph(id: String): BaseParagraphModel = mapper.map(dao.get(id))

    override suspend fun isNotEmpty(): Boolean = dao.getOneParagraph().isNotEmpty()
}
