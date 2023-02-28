package com.ribsky.domain.usecase.best

import com.ribsky.domain.model.best.BaseBestWordModel
import com.ribsky.domain.repository.BestWordRepository

interface GetBestWordUseCase {
    suspend fun getCurrentWord(): BaseBestWordModel

    suspend fun getBestWordById(id: Int): BaseBestWordModel
}

class GetBestWordUseCaseImpl(
    private val bestWordRepository: BestWordRepository,
) : GetBestWordUseCase {

    override suspend fun getCurrentWord(): BaseBestWordModel = bestWordRepository.getWord()
    override suspend fun getBestWordById(id: Int): BaseBestWordModel = bestWordRepository.getWordById(id)
}
