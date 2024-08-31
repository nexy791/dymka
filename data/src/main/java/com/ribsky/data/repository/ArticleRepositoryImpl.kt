package com.ribsky.data.repository

import com.ribsky.data.mapper.article.ArticleMapper
import com.ribsky.data.service.file.FileService
import com.ribsky.data.service.offline.article.ArticlesDao
import com.ribsky.data.service.online.article.ArticleService
import com.ribsky.data.utils.crypto.CryptoManager
import com.ribsky.data.utils.moshi.SliderAdapter
import com.ribsky.domain.model.article.BaseArticleModel
import com.ribsky.domain.model.article.slider.BaseSliderModel
import com.ribsky.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val articleService: ArticleService,
    private val fileService: FileService,
    private val articlesDao: ArticlesDao,
    private val cryptoManager: CryptoManager,
    private val mapper: ArticleMapper,
): ArticleRepository {
    override suspend fun loadArticles(): Result<List<BaseArticleModel>> {
        val lessonResult = articleService.loadArticles()
        lessonResult.onSuccess { articlesDao.insert(it) }
        return lessonResult.mapCatching { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getArticles(): List<BaseArticleModel> =
        articlesDao.get().map { mapper.map(it) }

    override suspend fun getArticle(id: String): BaseArticleModel =
        mapper.map(articlesDao.getById(id))

    override suspend fun getContent(content: String): Result<BaseSliderModel> {
        val resultFile = fileService.downloadAndGetFile(content)
        resultFile.fold(
            onSuccess = { file ->
                val resultModel = runCatching {
                    val json = cryptoManager.decryptFile(file)
                    val model = SliderAdapter.content.fromJson(json)!!
                    model
                }
                resultModel.fold(
                    onSuccess = {
                        return resultModel
                    },
                    onFailure = {
                        file.delete()
                        return resultModel
                    }
                )
            },
            onFailure = { ex ->
                return Result.failure(ex)
            }
        )
    }

    override suspend fun isNotEmpty(): Boolean = articlesDao.getOneArticle().isNotEmpty()
}