package com.ribsky.domain.usecase.bio

import com.ribsky.domain.repository.BioRepository

interface SetFromBioUseCase {

    fun invoke(id: Int)

}

class SetFromBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : SetFromBioUseCase {

    override fun invoke(id: Int) {
        bioRepository.saveFrom(id)
    }

}