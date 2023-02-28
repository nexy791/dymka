package com.ribsky.data.repository.test

import com.ribsky.data.mapper.test.TestMapper
import com.ribsky.data.service.file.FileService
import com.ribsky.data.service.offline.tests.TestsDao
import com.ribsky.data.service.online.test.TestService
import com.ribsky.data.utils.crypto.CryptoManager
import com.ribsky.data.utils.moshi.Adapters
import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.domain.model.word.BaseWordModel
import com.ribsky.domain.repository.TestRepository

class TestRepositoryImpl(
    private val fileService: FileService,
    private val testsDao: TestsDao,
    private val cryptoManager: CryptoManager,
    private val testService: TestService,
    private val mapper: TestMapper,
) : TestRepository {

    override suspend fun loadBooks(): Result<List<BaseTestModel>> {
        val lessonResult = testService.loadTests()
        lessonResult.onSuccess {
            testsDao.insert(it)
        }
        return lessonResult.mapCatching { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getTests(): List<BaseTestModel> =
        testsDao.get().map { mapper.map(it) } + AdditionTests.additionTests

    override suspend fun getContent(content: String): Result<List<BaseWordModel>> {
        val resultFile = fileService.downloadAndGetFile(content)
        resultFile.fold(
            onSuccess = { file ->
                val resultModel = runCatching {
                    val json = cryptoManager.decryptFile(file)
                    val model = Adapters.words.fromJson(json)!!
                    model
                }
                resultModel.fold(
                    onSuccess = {
                        return resultModel
                    },
                    onFailure = {
                        file.delete()
                        return resultModel
                    }
                )
            },
            onFailure = { ex ->
                return Result.failure(ex)
            }
        )
    }

    override suspend fun isNotEmpty(): Boolean = testsDao.getOneBook().isNotEmpty()
    override suspend fun getTestById(id: String): BaseTestModel {
        return if (id in AdditionTests.additionTests.map { it.id }) AdditionTests.additionTests.first { it.id == id }
        else mapper.map(testsDao.getBookById(id))
    }
}
