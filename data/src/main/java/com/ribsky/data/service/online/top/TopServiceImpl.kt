package com.ribsky.data.service.online.top

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.ribsky.data.model.TopApiModel
import kotlinx.coroutines.tasks.await

class TopServiceImpl(
    private val db: FirebaseDatabase,
) : TopService {

    override suspend fun loadUsers(): Result<List<TopApiModel>> {
        val users = mutableListOf<TopApiModel>()
        val type = object : GenericTypeIndicator<Map<String, TopApiModel>>() {}
        val usersResult = runCatching {
            users.addAll(
                db.reference.root.child("users").orderByChild("score").limitToLast(50).get().await()
                    .getValue(type)!!.values.map { it.copy(type = TopApiModel.Type.TEST) }
            )
            users.addAll(
                db.reference.root.child("users").orderByChild("lessonsCount").limitToLast(50).get()
                    .await()
                    .getValue(type)!!.values.map { it.copy(type = TopApiModel.Type.LESSON) }
            )
            users.addAll(
                db.reference.root.child("users").orderByChild("streak").limitToLast(50).get()
                    .await()
                    .getValue(type)!!.values.map { it.copy(type = TopApiModel.Type.STREAK) }
            )
            users.addAll(
                db.reference.root.child("users").orderByChild("hasPrem").limitToLast(50).get()
                    .await()
                    .getValue(type)!!.values.map { it.copy(type = TopApiModel.Type.PREMIUM) }
            )
            users
        }
        return usersResult
    }
}
