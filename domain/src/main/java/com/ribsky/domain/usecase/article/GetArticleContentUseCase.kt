package com.ribsky.domain.usecase.article

import com.ribsky.domain.model.article.slider.BaseSliderModel
import com.ribsky.domain.repository.ArticleRepository

interface GetArticleContentUseCase {

    suspend fun invoke(content: String): Result<BaseSliderModel>
}

class GetArticleContentUseCaseImpl(
    private val articleRepository: ArticleRepository,
) : GetArticleContentUseCase {

    override suspend fun invoke(content: String): Result<BaseSliderModel> =
        articleRepository.getContent(content)
}
