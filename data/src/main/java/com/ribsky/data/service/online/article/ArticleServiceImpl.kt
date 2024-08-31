package com.ribsky.data.service.online.article

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ribsky.data.model.ArticleApiModel
import kotlinx.coroutines.tasks.await

class ArticleServiceImpl(
    private val db: FirebaseFirestore,
): ArticleService {
    override suspend fun loadArticles(): Result<List<ArticleApiModel>> {
        val articlesResult = runCatching {
            db.collection("articles")
                .get()
                .await().documents.map { document ->
                    document.toObject<ArticleApiModel>()!!
                }
        }
        return articlesResult
    }

}