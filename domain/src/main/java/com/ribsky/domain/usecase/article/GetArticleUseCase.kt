package com.ribsky.domain.usecase.article

import com.ribsky.domain.model.article.BaseArticleModel
import com.ribsky.domain.repository.ArticleRepository

interface GetArticleUseCase {

    suspend fun invoke(id: String): BaseArticleModel
}

class GetArticleUseCaseImpl(
    private val articleRepository: ArticleRepository,
) : GetArticleUseCase {

    override suspend fun invoke(id: String): BaseArticleModel =
        articleRepository.getArticle(id)
}
