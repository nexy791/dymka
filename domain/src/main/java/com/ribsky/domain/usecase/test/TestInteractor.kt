package com.ribsky.domain.usecase.test

import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.domain.repository.TestRepository

interface TestInteractor {

    suspend fun getTests(): List<BaseTestModel>

    suspend fun getTest(id: String): BaseTestModel
}

class TestInteractorImpl(
    private val testRepository: TestRepository,
) : TestInteractor {
    override suspend fun getTests(): List<BaseTestModel> = testRepository.getTests()

    override suspend fun getTest(id: String): BaseTestModel = testRepository.getTestById(id)
}
