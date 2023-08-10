package com.ribsky.data.service.online.top

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.ribsky.data.model.TopApiModel
import com.ribsky.data.service.user.UserService
import kotlinx.coroutines.tasks.await

class TopServiceImpl(
    private val db: FirebaseDatabase,
    private val userService: UserService,
) : TopService {

    override suspend fun loadUsers(): Result<List<TopApiModel>> {
        val user = userService.getUserFromCache()
        val users = mutableListOf<TopApiModel>()
        val type = object : GenericTypeIndicator<Map<String, TopApiModel>>() {}
        val usersResult = runCatching {
            users.addAll(
                db.reference.root.child("users").orderByChild("score").limitToLast(50).get().await()
                    .getValue(type)!!.values.map { it.copy(type = TopApiModel.Type.TEST) }
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
            users.addAll(
                db.reference.root.child("users").orderByChild("starsCount").limitToLast(50).get()
                    .await()
                    .getValue(type)!!.values.map { it.copy(type = TopApiModel.Type.STAR) }
            )
            users.addAll(
                db.reference.root.child("users").orderByChild("starsCount")
                    .endBefore(firebaseFix(userService.getUserFromCache()?.starsCount, true))
                    .limitToLast(5).get().await()
                    .getValue(type)?.values.orEmpty()
                    .map { it.copy(type = TopApiModel.Type.NEAR_STARS) }
                    .filter { it.name != user?.name && it.image != user?.image }
                    .distinctBy { it.starsCount }
            )
            users.addAll(
                db.reference.root.child("users").orderByChild("starsCount")
                    .startAfter(firebaseFix(userService.getUserFromCache()?.starsCount, false))
                    .limitToFirst(50).get().await()
                    .getValue(type)?.values.orEmpty()
                    .map { it.copy(type = TopApiModel.Type.NEAR_STARS) }
                    .filter { it.name != user?.name && it.image != user?.image }
                    .distinctBy { it.starsCount }
            )

            users.addAll(
                db.reference.root.child("users").orderByChild("score")
                    .endBefore(firebaseFix(userService.getUserFromCache()?.score, true))
                    .limitToLast(5).get().await()
                    .getValue(type)?.values.orEmpty()
                    .map { it.copy(type = TopApiModel.Type.NEAR_TEST) }
                    .filter { it.name != user?.name && it.image != user?.image }
                    .distinctBy { it.score }
            )

            users.addAll(
                db.reference.root.child("users").orderByChild("score")
                    .startAfter(firebaseFix(userService.getUserFromCache()?.score, false))
                    .limitToFirst(50).get().await()
                    .getValue(type)?.values.orEmpty()
                    .map { it.copy(type = TopApiModel.Type.NEAR_TEST) }
                    .filter { it.name != user?.name && it.image != user?.image }
                    .distinctBy { it.score }
            )

            user?.let {
                users.add(TopApiModel.fromUserApi(it, TopApiModel.Type.NEAR_STARS))
                users.add(TopApiModel.fromUserApi(it, TopApiModel.Type.NEAR_TEST))
            }
            users
        }
        return usersResult
    }

    private fun firebaseFix(score: Int?, isEnd: Boolean): Double {
        return (if (score == null) {
            if (isEnd) 0 else 1
        } else {
            if(isEnd) score - 1 else score + 1
        }).toDouble()

    }
}
