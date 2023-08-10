package com.ribsky.domain.usecase.bio

import com.ribsky.domain.model.bio.BaseFromModel
import com.ribsky.domain.repository.BioRepository

interface GetFromsBioUseCase {

    fun invoke(): List<BaseFromModel>

}

class GetFromsBioUseCaseImpl(
    private val bioRepository: BioRepository,
) : GetFromsBioUseCase {

    override fun invoke(): List<BaseFromModel> = bioRepository.getFromsList()

}
