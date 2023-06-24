package com.ribsky.domain.usecase.bio

import com.ribsky.domain.repository.BioRepository

interface SetGoalBioUseCase {

    fun invoke(id: Int)

}

class SetGoalBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : SetGoalBioUseCase {

    override fun invoke(id: Int) {
        bioRepository.saveGoal(id)
    }

}