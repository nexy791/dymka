package com.ribsky.domain.usecase.file

import com.ribsky.domain.repository.FileRepository

interface IsContentExistsUseCase {
    fun invoke(content: String): Boolean
}

class IsContentExistsUseCaseImpl(
    private val fileRepository: FileRepository,
) : IsContentExistsUseCase {

    override fun invoke(content: String): Boolean = fileRepository.isFileExists(content)
}
