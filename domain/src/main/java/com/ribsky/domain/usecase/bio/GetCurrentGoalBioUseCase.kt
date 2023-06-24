package com.ribsky.domain.usecase.bio

import com.ribsky.domain.model.bio.BaseGoalModel
import com.ribsky.domain.repository.BioRepository

interface GetCurrentGoalBioUseCase {

    fun invoke(): BaseGoalModel?

}

class GetCurrentGoalBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : GetCurrentGoalBioUseCase {

    override fun invoke(): BaseGoalModel? =
        bioRepository.getGoal()?.let { bioRepository.getGoalById(it) }

}