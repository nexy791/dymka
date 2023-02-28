package com.ribsky.data.repository

import com.ribsky.data.mapper.lesson.LessonMapper
import com.ribsky.data.model.ContentApiModel.Companion.shuffle
import com.ribsky.data.service.file.FileService
import com.ribsky.data.service.offline.lesson.LessonsDao
import com.ribsky.data.service.online.lesson.LessonService
import com.ribsky.data.utils.crypto.CryptoManager
import com.ribsky.data.utils.moshi.ContentAdapter
import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.domain.repository.LessonRepository

class LessonRepositoryImpl(
    private val lessonService: LessonService,
    private val fileService: FileService,
    private val lessonsDao: LessonsDao,
    private val cryptoManager: CryptoManager,
    private val mapper: LessonMapper,
) : LessonRepository {

    override suspend fun loadLessons(): Result<List<BaseLessonModel>> {
        val lessonResult = lessonService.loadLessons()
        lessonResult.onSuccess {
            lessonsDao.insert(it)
        }
        return lessonResult.mapCatching { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getLessons(): List<BaseLessonModel> =
        lessonsDao.get().map { mapper.map(it) }

    override suspend fun getLessons(paragraphId: String): List<BaseLessonModel> =
        lessonsDao.get(paragraphId).map { mapper.map(it) }

    override suspend fun getLesson(id: String): BaseLessonModel = mapper.map(lessonsDao.getById(id))

    override suspend fun getContent(content: String): Result<BaseContentModel> {

        val resultFile = fileService.downloadAndGetFile(content)
        resultFile.fold(
            onSuccess = { file ->
                val resultModel = runCatching {
                    val json = cryptoManager.decryptFile(file)
                    val model = ContentAdapter.content.fromJson(json)!!
                    model
                }

                resultModel.fold(
                    onSuccess = {
                        return resultModel.map { it.shuffle() }
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

    override suspend fun isNotEmpty(): Boolean = lessonsDao.getOneLesson().isNotEmpty()
}
