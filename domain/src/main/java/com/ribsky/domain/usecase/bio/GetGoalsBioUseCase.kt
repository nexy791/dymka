package com.ribsky.domain.usecase.bio

import com.ribsky.domain.model.bio.BaseGoalModel
import com.ribsky.domain.repository.BioRepository

interface GetGoalsBioUseCase {

    fun invoke(): List<BaseGoalModel>

}

class GetGoalsBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : GetGoalsBioUseCase {

    override fun invoke(): List<BaseGoalModel> = bioRepository.getGoalsList()

}