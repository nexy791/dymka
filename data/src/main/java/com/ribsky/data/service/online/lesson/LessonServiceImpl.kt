package com.ribsky.data.service.online.lesson

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.ribsky.data.model.LessonApiModel
import kotlinx.coroutines.tasks.await

class LessonServiceImpl(
    private val db: FirebaseFirestore,
) : LessonService {

    override suspend fun loadLessons(): Result<List<LessonApiModel>> {
        val lessonsResult = runCatching {
            db.collection("lessons")
                .get()
                .await().documents.map { document ->
                    document.toObject<LessonApiModel>()!!
                }
        }
        return lessonsResult
    }
}
