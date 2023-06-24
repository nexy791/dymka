package com.ribsky.domain.usecase.paragraph

import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.domain.model.paragraph.BaseParagraphModel
import com.ribsky.domain.repository.ActiveRepository
import com.ribsky.domain.repository.LessonRepository
import com.ribsky.domain.repository.ParagraphRepository

interface ParagraphInteractor {

    suspend fun getParagraphs(): List<BaseParagraphModel>

    suspend fun getParagraph(id: String): BaseParagraphModel
}

class ParagraphInteractorImpl(
    private val paragraphRepository: ParagraphRepository,
    private val activeRepository: ActiveRepository,
    private val lessonRepository: LessonRepository,
) : ParagraphInteractor {

    override suspend fun getParagraphs(): List<BaseParagraphModel> {
        val paragraphs = paragraphRepository.getParagraphs().sortedBy { it.sort }
        val activeLessons = activeRepository.getActiveLessons()
        val lessons = lessonRepository.getLessons()
        var paragraphsToReturn = calculateProgressOfParagraphs(paragraphs, activeLessons, lessons)
        paragraphsToReturn = calculateIsCanBeOpened(paragraphsToReturn, activeLessons, lessons)
        return paragraphsToReturn
    }

    override suspend fun getParagraph(id: String): BaseParagraphModel {
        val paragraph = paragraphRepository.getParagraph(id)
        val activeLessons = activeRepository.getActiveLessons()
        val lessons = lessonRepository.getLessons(id)
        return calculateProgressOfParagraph(paragraph, activeLessons, lessons)
    }

    private fun calculateProgressOfParagraphs(
        paragraphs: List<BaseParagraphModel>,
        activeLessons: List<String>,
        lessons: List<BaseLessonModel>,
    ): List<BaseParagraphModel> {
        paragraphs.forEach { paragraph ->
            val paragraphLessons = lessons.filter { lesson -> lesson.paragraphId == paragraph.id }
            calculateProgressOfParagraph(paragraph, activeLessons, paragraphLessons)
        }
        return paragraphs
    }

    private fun calculateIsCanBeOpened(
        paragraphs: List<BaseParagraphModel>,
        activeLessons: List<String>,
        lessons: List<BaseLessonModel>,
    ): List<BaseParagraphModel> {
        paragraphs.forEach { paragraph ->
            val paragraphLessons = lessons.filter { lesson -> lesson.paragraphId == paragraph.id }
            val freeLessons = paragraphLessons.filter { lesson -> !lesson.hasPrem }
            paragraph.isCanBeOpened = freeLessons.all { lesson ->
                activeLessons.any { it == lesson.id }
            } || paragraphLessons.isEmpty() || paragraph.sort == 0
        }
        return paragraphs
    }

    private fun calculateProgressOfParagraph(
        paragraph: BaseParagraphModel,
        activeLessons: List<String>,
        lessons: List<BaseLessonModel>,
    ): BaseParagraphModel {
        val paragraphActiveLessons = lessons.filter { lesson ->
            activeLessons.any { active -> active == lesson.id }
        }
        val percent = calculatePercent(paragraphActiveLessons.size, lessons.size)
        return paragraph.apply {
            this.percent = percent
            this.isEmpty = lessons.isEmpty()
        }
    }

    private fun calculatePercent(activeLessons: Int, lessons: Int): Float {
        return if (lessons != 0) {
            (activeLessons * 100 / lessons).toFloat()
        } else {
            0f
        }
    }
}
