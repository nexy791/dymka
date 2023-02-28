package com.ribsky.domain.usecase.lesson

import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.domain.repository.LessonRepository

interface GetLessonContentUseCase {

    suspend fun invoke(content: String): Result<BaseContentModel>
}

class GetLessonContentUseCaseImpl(
    private val lessonRepository: LessonRepository,
) : GetLessonContentUseCase {
    override suspend fun invoke(content: String): Result<BaseContentModel> =
        lessonRepository.getContent(content)
}
