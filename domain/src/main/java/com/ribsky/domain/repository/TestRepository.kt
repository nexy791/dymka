package com.ribsky.domain.repository

import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.domain.model.word.BaseWordModel

interface TestRepository {

    suspend fun loadBooks(): Result<List<BaseTestModel>>

    suspend fun getTests(): List<BaseTestModel>

    suspend fun getContent(content: String): Result<List<BaseWordModel>>

    suspend fun isNotEmpty(): Boolean
    suspend fun getTestById(id: String): BaseTestModel
}
