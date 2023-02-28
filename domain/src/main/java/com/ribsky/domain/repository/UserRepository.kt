package com.ribsky.domain.repository

import com.ribsky.domain.model.user.BaseUserModel

interface UserRepository {

    suspend fun getUserOnline(token: String?): Result<BaseUserModel?>
    suspend fun getUserOffline(): BaseUserModel?

    suspend fun updateUserOffline(token: String?, user: BaseUserModel)
    suspend fun syncUser(token: String?): Result<BaseUserModel>
}
