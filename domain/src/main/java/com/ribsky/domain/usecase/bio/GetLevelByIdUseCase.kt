package com.ribsky.domain.usecase.bio

import com.ribsky.domain.model.bio.BaseGoalModel
import com.ribsky.domain.model.bio.BaseLevelModel
import com.ribsky.domain.repository.BioRepository

interface GetLevelByIdUseCase {

    fun invoke(id: Int): BaseLevelModel?

}

class GetLevelByIdUseCaseImpl(
    private val bioRepository: BioRepository
) : GetLevelByIdUseCase {

    override fun invoke(id: Int): BaseLevelModel? = bioRepository.getLevelById(id)

}