package com.ribsky.domain.usecase.bio

import com.ribsky.domain.model.bio.BaseFromModel
import com.ribsky.domain.model.bio.BaseGoalModel
import com.ribsky.domain.repository.BioRepository

interface GetCurrentFromBioUseCase {

    fun invoke(): BaseFromModel?

}

class GetCurrentFromBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : GetCurrentFromBioUseCase {

    override fun invoke(): BaseFromModel? =
        bioRepository.getFrom()?.let { bioRepository.getFromById(it) }

}