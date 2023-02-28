package com.ribsky.domain.repository

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.domain.model.lesson.BaseLessonModel

interface LessonRepository {

    suspend fun loadLessons(): Result<List<BaseLessonModel>>

    suspend fun getLessons(): List<BaseLessonModel>

    suspend fun getLessons(paragraphId: String): List<BaseLessonModel>

    suspend fun getLesson(id: String): BaseLessonModel

    suspend fun getContent(content: String): Result<BaseContentModel>

    suspend fun isNotEmpty(): Boolean
}
