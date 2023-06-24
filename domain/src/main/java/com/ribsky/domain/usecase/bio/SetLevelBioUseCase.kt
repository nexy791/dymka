package com.ribsky.domain.usecase.bio

import com.ribsky.domain.repository.BioRepository

interface SetLevelBioUseCase {

    fun invoke(id: Int)

}

class SetLevelBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : SetLevelBioUseCase {

    override fun invoke(id: Int) {
        bioRepository.saveLevel(id)
    }

}