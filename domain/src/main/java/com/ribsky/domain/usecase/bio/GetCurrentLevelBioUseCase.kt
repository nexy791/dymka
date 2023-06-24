package com.ribsky.domain.usecase.bio

import com.ribsky.domain.model.bio.BaseLevelModel
import com.ribsky.domain.repository.BioRepository

interface GetCurrentLevelBioUseCase {

    fun invoke(): BaseLevelModel?

}

class GetCurrentLevelBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : GetCurrentLevelBioUseCase {

    override fun invoke(): BaseLevelModel? =
        bioRepository.getLevel()?.let { bioRepository.getLevelById(it) }

}