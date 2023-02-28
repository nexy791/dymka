package com.ribsky.domain.repository

interface FileRepository {

    fun isFileExists(content: String): Boolean
}
