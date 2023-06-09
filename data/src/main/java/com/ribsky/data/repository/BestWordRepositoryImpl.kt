package com.ribsky.data.repository

import com.ribsky.data.mapper.best.BestMapper
import com.ribsky.data.service.offline.best.BestWordDao
import com.ribsky.data.service.online.best.BestWordService
import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.repository.BestWordRepository

class BestWordRepositoryImpl(
    private val bestWordService: BestWordService,
    private val mapper: BestMapper,
    private val bestWordDao: BestWordDao,
) : BestWordRepository {

    override suspend fun loadWords(): Result<List<BaseBestWordModel>> {
        val result = bestWordService.loadWords()
        result.onSuccess {
            bestWordDao._insert(it)
        }
        return result.mapCatching { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getWord(): BaseBestWordModel {
        val word = bestWordService.getCurrentBestWord()
        return mapper.map(word)
    }

    override suspend fun getWordById(id: Int): BaseBestWordModel {
        val word = bestWordDao.get(id)
        return mapper.map(word!!)
    }

    override suspend fun isNotEmpty(): Boolean = bestWordDao.getOneWord().isNotEmpty()
}
