package com.ribsky.data.repository

import com.ribsky.data.service.file.FileService
import com.ribsky.domain.repository.FileRepository

class FileRepositoryImpl(
    private val fileService: FileService,
) : FileRepository {

    override fun isFileExists(content: String): Boolean =
        fileService.isFileExists(content)
}
