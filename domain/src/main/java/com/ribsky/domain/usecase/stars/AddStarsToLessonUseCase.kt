package com.ribsky.domain.usecase.stars

import com.ribsky.domain.repository.ActiveRepository

interface AddStarsToLessonUseCase {

    suspend fun invoke(id: String, stars: Int)
}

class AddStarsToLessonUseCaseImpl(
    private val activeRepository: ActiveRepository,
) : AddStarsToLessonUseCase {

    override suspend fun invoke(id: String, stars: Int) {
       activeRepository.addStars(id, stars)
    }

}
