package com.ribsky.data.service.user

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.firebase.database.FirebaseDatabase
import com.ribsky.billing.manager.SubManager
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.data.model.UserApiModel
import com.ribsky.data.utils.moshi.Adapters
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.repository.ActiveRepository
import com.ribsky.domain.repository.SaveRepository
import kotlinx.coroutines.tasks.await
import java.util.*

class UserServiceImpl(
    private val database: FirebaseDatabase,
    private val sharedPreferences: SharedPreferences,
    private val activeRepository: ActiveRepository,
    private val saveRepository: SaveRepository,
    private val subManager: SubManager,
    private val internetManager: InternetManager,
) : UserService {

    override suspend fun getUserOnline(token: String?): Result<UserApiModel?> = runCatching {
        if (internetManager.isOnline()) {
            val user = database.reference.root.child("users").child(token!!).get().await()
                .getValue(UserApiModel::class.java)
            user
        } else {
            throw Throwable("Offline get")
        }
    }

    override suspend fun getUserFromCache(): UserApiModel? = runCatching {
        val json = sharedPreferences.getString(KEY_USER, null)
        val result = json?.let { Adapters.user.fromJson(it) }
        result?.apply {
            val mLessons = activeRepository.getActiveLessons()
            score = activeRepository.getTestScore()
            lessons = mLessons.transformToHashMapString()
            saved = saveRepository.getWordsList().transformToHashMapString()
            lessonsCount = mLessons.size
        }
    }.getOrNull()

    override suspend fun setUserOnline(token: String?, user: BaseUserModel): Result<Unit> =
        runCatching {
            if (internetManager.isOnline()) {
                database.reference.root.child("users").child(token!!)
                    .setValue(
                        user.apply {
                            version += 1
                        }
                    ).await()
                setUserToCache(user.apply { version += 1 })
            } else {
                throw Throwable("Offline set")
            }
        }

    override suspend fun setUserToCache(user: BaseUserModel) {
        sharedPreferences.edit {
            val json = Adapters.user.toJson(
                UserApiModel(user).apply {
                    hasPrem = subManager.isSub()
                }
            )
            putString(KEY_USER, json)
        }
        activeRepository.setTestScore(user.score)
        activeRepository.setActiveLessons(user.lessons.transformToListString())
        saveRepository.setWordsList(user.saved.transformToListString())
    }

    override suspend fun sync(token: String?): Result<BaseUserModel> = runCatching {
        val user = getUserFromCache()!!
        if (internetManager.isOnline()) {
            val userOnline = getUserOnline(token!!).getOrNull()!!
            return@runCatching if (userOnline.version > user.version) {
                setUserToCache(userOnline)
                userOnline
            } else {
                database.reference.root.child("users").child(token)
                    .setValue(
                        user.apply {
                            version += 1
                            hasPrem = subManager.isSub()
                        }
                    ).await()
                setUserToCache(user.apply { version += 1 })
                user.apply { version += 1 }
            }
        } else {
            throw Throwable("Offline sync")
        }
    }

    private fun List<String>.transformToHashMapString(): Map<String, String> {
        val map = HashMap<String, String>()
        forEach { map[UUID.randomUUID().toString()] = it }
        return map
    }

    private fun Map<String, String>.transformToListString(): List<String> = values.toList()

    private companion object {
        private const val KEY_USER = "KEY_USER"
    }
}
