package com.ribsky.data.service.file

import java.io.File

interface FileService {

    suspend fun downloadAndGetFile(content: String): Result<File>

    suspend fun delete()

    fun isFileExists(content: String): Boolean
}
