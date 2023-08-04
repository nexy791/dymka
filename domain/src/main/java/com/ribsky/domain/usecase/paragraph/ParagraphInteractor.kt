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
        val stars = activeRepository.getStars()

        var paragraphsToReturn = calculateProgressOfParagraphs(paragraphs, activeLessons, lessons)
        paragraphsToReturn = calculateIsCanBeOpened(paragraphsToReturn, lessons, stars)
        return paragraphsToReturn
    }

    override suspend fun getParagraph(id: String): BaseParagraphModel {
        val paragraph = paragraphRepository.getParagraph(id)
        val activeLessons = activeRepository.getActiveLessons()
        val lessons = lessonRepository.getLessons(id)
        return calculateProgressOfParagraph(paragraph, activeLessons, lessons)
    }

    private fun calculateIsCanBeOpened(
        paragraphs: List<BaseParagraphModel>,
        lessons: List<BaseLessonModel>,
        stars: Map<String, Int>,
    ): List<BaseParagraphModel> {
        val paragraphsSorted = paragraphs.sortedBy { it.sort }
        var previousParagraph: BaseParagraphModel = paragraphsSorted[0]

        val list = paragraphsSorted.mapIndexed { index, baseParagraphModel ->

            val previousParagraphLessons =
                lessons.filter { it.paragraphId == previousParagraph.id }

            val previousParagraphStars =
                previousParagraphLessons.sumOf { stars[it.id] ?: 0 }

            baseParagraphModel.starsHave = previousParagraphStars

            if (index != 0) {
                if (!previousParagraph.isEnoughStars) {
                    baseParagraphModel.isEnoughStars = false
                } else {
                    baseParagraphModel.isEnoughStars =
                        previousParagraphStars >= baseParagraphModel.stars
                }
            } else {
                baseParagraphModel.isEnoughStars = true
            }
            previousParagraph = baseParagraphModel
            baseParagraphModel
        }
        return list
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
        return if (lessons != 0) (activeLessons * 100 / lessons).toFloat()
        else 0f
    }
}
