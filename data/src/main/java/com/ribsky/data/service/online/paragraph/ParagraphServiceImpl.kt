package com.ribsky.data.service.online.paragraph

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.ribsky.data.model.ParagraphApiModel
import kotlinx.coroutines.tasks.await

class ParagraphServiceImpl(
    private val db: FirebaseFirestore,
) : ParagraphService {

    override suspend fun loadParagraphs(): Result<List<ParagraphApiModel>> {
        val result = runCatching {
            db.collection("paragraph")
                .orderBy("id", Query.Direction.ASCENDING).get().await().documents.map { document ->
                    document.toObject<ParagraphApiModel>()!!
                }
        }
        return result
    }
}
