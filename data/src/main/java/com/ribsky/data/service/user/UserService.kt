package com.ribsky.data.service.user

import com.ribsky.data.model.UserApiModel
import com.ribsky.domain.model.user.BaseUserModel

interface UserService {

    suspend fun getUserOnline(token: String?): Result<UserApiModel?>
    suspend fun getUserFromCache(): UserApiModel?

    suspend fun setUserOnline(token: String?, user: BaseUserModel): Result<Unit>

    suspend fun setUserToCache(user: BaseUserModel)

    suspend fun sync(token: String?): Result<BaseUserModel>
}
