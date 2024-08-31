package com.ribsky.data.service.online.article

import com.ribsky.data.model.ArticleApiModel

interface ArticleService {

    suspend fun loadArticles(): Result<List<ArticleApiModel>>
}
