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
        val stars = activeRepository.getStars()
        return calculateProgressOfLessons(lessons, activeLessons, stars)
    }

    override suspend fun getLessons(paragraphId: String): List<BaseLessonModel> {
        val lessons = lessonsRepository.getLessons(paragraphId)
        val activeLessons = activeRepository.getActiveLessons()
        val stars = activeRepository.getStars()
        return calculateProgressOfLessons(lessons, activeLessons, stars)
    }

    override suspend fun getLesson(id: String): BaseLessonModel {
        val lesson = lessonsRepository.getLesson(id)
        val activeLessons = activeRepository.getActiveLessons()
        val stars = activeRepository.getStars()
        return calculateProgressOfLesson(lesson, activeLessons, stars)
    }

    private fun calculateProgressOfLesson(
        lesson: BaseLessonModel,
        activeLessons: List<String>,
        stars: Map<String, Int>,
    ): BaseLessonModel {
        lesson.isDone = activeLessons.any { active -> active == lesson.id }
        lesson.stars = stars[lesson.id] ?: 0
        return lesson
    }

    private fun calculateProgressOfLessons(
        lessons: List<BaseLessonModel>,
        activeLessons: List<String>,
        stars: Map<String, Int>,
    ): List<BaseLessonModel> {
        lessons.forEach { lesson ->
            val lessonCalculated = calculateProgressOfLesson(lesson, activeLessons, stars)
            lesson.isDone = lessonCalculated.isDone
            lesson.stars = lessonCalculated.stars
        }
        return lessons
    }
}
