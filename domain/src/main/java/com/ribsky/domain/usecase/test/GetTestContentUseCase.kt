package com.ribsky.domain.usecase.test

import com.ribsky.domain.model.word.BaseWordModel
import com.ribsky.domain.repository.TestRepository

interface GetTestContentUseCase {

    suspend fun getContent(content: String): Result<List<BaseWordModel>>

    suspend fun getContentAll(): Result<List<BaseWordModel>>
}

class GetTestContentUseCaseImpl(
    private val testRepository: TestRepository,
) : GetTestContentUseCase {
    override suspend fun getContent(content: String): Result<List<BaseWordModel>> =
        testRepository.getContent(content)

    override suspend fun getContentAll(): Result<List<BaseWordModel>> {
        val tests = testRepository.getTests()
        val result = mutableListOf<BaseWordModel>()
        tests.forEach {
            testRepository.getContent(it.content).getOrNull()?.let { r -> result.addAll(r) }
        }
        return Result.success(result)
    }
}
