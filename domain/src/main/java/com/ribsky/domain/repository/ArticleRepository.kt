package com.ribsky.domain.repository

import com.ribsky.domain.model.article.BaseArticleModel
import com.ribsky.domain.model.article.slider.BaseSliderModel
import com.ribsky.domain.model.content.BaseContentModel
import com.ribsky.domain.model.lesson.BaseLessonModel

interface ArticleRepository {

    suspend fun loadArticles(): Result<List<BaseArticleModel>>

    suspend fun getArticles(): List<BaseArticleModel>

    suspend fun getArticle(id: String): BaseArticleModel

    suspend fun getContent(content: String): Result<BaseSliderModel>

    suspend fun isNotEmpty(): Boolean
}
