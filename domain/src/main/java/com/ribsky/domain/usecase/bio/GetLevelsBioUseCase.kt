package com.ribsky.domain.usecase.bio

import com.ribsky.domain.model.bio.BaseLevelModel
import com.ribsky.domain.repository.BioRepository

interface GetLevelsBioUseCase {

    fun invoke(): List<BaseLevelModel>

}

class GetLevelsBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : GetLevelsBioUseCase {

    override fun invoke(): List<BaseLevelModel> = bioRepository.getLevelsList()

}
