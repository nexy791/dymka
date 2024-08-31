package com.ribsky.domain.usecase.article

import com.ribsky.domain.model.article.BaseArticleModel
import com.ribsky.domain.repository.ArticleRepository

interface GetArticlesUseCase {

    suspend fun invoke(): List<BaseArticleModel>
}

class GetArticlesUseCaseImpl(
    private val articleRepository: ArticleRepository,
) : GetArticlesUseCase {

    override suspend fun invoke(): List<BaseArticleModel> =
        articleRepository.getArticles()
}
