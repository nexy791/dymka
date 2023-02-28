package com.ribsky.data.service.online.test

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.ribsky.data.model.TestApiModel
import kotlinx.coroutines.tasks.await

class TestServiceImpl(
    private val db: FirebaseFirestore,
) : TestService {

    override suspend fun loadTests(): Result<List<TestApiModel>> {
        val wordsResult =
            db.collection("books")
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .await().documents.map { document ->
                    document.toObject<TestApiModel>()!!
                }
        return Result.success(wordsResult)
    }
}
