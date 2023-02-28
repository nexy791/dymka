package com.ribsky.data.repository

import com.ribsky.data.service.user.UserService
import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userService: UserService,
) : UserRepository {

    override suspend fun getUserOnline(token: String?): Result<BaseUserModel?> =
        userService.getUserOnline(token)

    override suspend fun getUserOffline(): BaseUserModel? =
        userService.getUserFromCache()

    override suspend fun updateUserOffline(token: String?, user: BaseUserModel) =
        userService.setUserToCache(user)

    override suspend fun syncUser(token: String?): Result<BaseUserModel> = userService.sync(token)
}
