package com.ribsky.domain.usecase.bio

import com.ribsky.domain.repository.BioRepository

interface IsNeedToFillBioUseCase {

    fun invoke(): Boolean

}

class IsNeedToFillBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : IsNeedToFillBioUseCase {

    override fun invoke(): Boolean =
        bioRepository.getGoal() == null || bioRepository.getLevel() == null || bioRepository.getFrom() == null

}