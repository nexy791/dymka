package com.ribsky.domain.repository

import com.ribsky.domain.model.best.BaseBestWordModel

interface BestWordRepository {

    suspend fun loadWords(): Result<List<BaseBestWordModel>>

    suspend fun getWord(): BaseBestWordModel

    suspend fun getWordById(id: Int): BaseBestWordModel
    suspend fun isNotEmpty(): Boolean
}
