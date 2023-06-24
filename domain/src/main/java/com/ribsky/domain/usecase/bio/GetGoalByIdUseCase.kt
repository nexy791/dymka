package com.ribsky.domain.usecase.bio

import com.ribsky.domain.model.bio.BaseGoalModel
import com.ribsky.domain.repository.BioRepository

interface GetGoalByIdUseCase {

    fun invoke(id: Int): BaseGoalModel?

}

class GetGoalByIdUseCaseImpl(
    private val bioRepository: BioRepository,
) : GetGoalByIdUseCase {

    override fun invoke(id: Int): BaseGoalModel? = bioRepository.getGoalById(id)

}