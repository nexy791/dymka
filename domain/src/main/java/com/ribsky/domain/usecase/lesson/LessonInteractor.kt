package com.ribsky.domain.usecase.lesson

import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.domain.repository.ActiveRepository
import com.ribsky.domain.repository.LessonRepository

interface LessonInteractor {

    suspend fun getLessons(): List<BaseLessonModel>

    suspend fun getLessons(paragraphId: String): List<BaseLessonModel>

    suspend fun getLesson(id: String): BaseLessonModel
}

class LessonInteractorImpl(
    private val lessonsRepository: LessonRepository,
    private val activeRepository: ActiveRepository,
) : LessonInteractor {

    override suspend fun getLessons(): List<BaseLessonModel> {
        val lessons = lessonsRepository.getLessons()
        val activeLessons = activeRepository.getActiveLessons()
        return calculateProgressOfLessons(lessons, activeLessons)
    }

    override suspend fun getLessons(paragraphId: String): List<BaseLessonModel> {
        val lessons = lessonsRepository.getLessons(paragraphId)
        val activeLessons = activeRepository.getActiveLessons()
        return calculateProgressOfLessons(lessons, activeLessons)
    }

    override suspend fun getLesson(id: String): BaseLessonModel {
        val lesson = lessonsRepository.getLesson(id)
        val activeLessons = activeRepository.getActiveLessons()
        return calculateProgressOfLesson(lesson, activeLessons)
    }

    private fun calculateProgressOfLesson(
        lesson: BaseLessonModel,
        activeLessons: List<String>,
    ): BaseLessonModel {
        lesson.isDone = activeLessons.any { active -> active == lesson.id }
        return lesson
    }

    private fun calculateProgressOfLessons(
        lessons: List<BaseLessonModel>,
        activeLessons: List<String>,
    ): List<BaseLessonModel> {
        lessons.forEach { lesson ->
            lesson.isDone = calculateProgressOfLesson(lesson, activeLessons).isDone
        }
        return lessons
    }
}
