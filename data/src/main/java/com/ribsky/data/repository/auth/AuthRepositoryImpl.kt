package com.ribsky.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.ribsky.data.model.UserApiModel
import com.ribsky.data.service.user.UserService
import com.ribsky.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val userService: UserService,
) : AuthRepository {

    override val token: String? get() = auth.currentUser?.uid

    override suspend fun authUser(token: String): Result<Unit> {
        val firebaseCredential = GoogleAuthProvider.getCredential(token, null)
        val firebaseUser =
            runCatching { auth.signInWithCredential(firebaseCredential).await().user!! }
        firebaseUser.fold(
            onSuccess = {
                val userApi = it.toUserApi()
                val getUser = userService.getUserOnline(it.uid)
                getUser.fold(
                    onSuccess = { user ->
                        if (user == null) {
                            return userService.setUserOnline(this.token, userApi)
                        } else {
                            val result = userService.setUserToCache(user)
                            return Result.success(result)
                        }
                    },
                    onFailure = { ex ->
                        auth.signOut()
                        return Result.failure(ex)
                    }
                )
            },
            onFailure = { ex ->
                auth.signOut()
                return Result.failure(ex)
            }
        )
    }

    override suspend fun isAuth(): Boolean = userService.getUserFromCache() != null

    override fun signOut() {
        auth.signOut()
    }

    private fun FirebaseUser.toUserApi() = UserApiModel(
        name = displayName.toString(),
        email = email.toString(),
        image = photoUrl.toString()
    )
}
