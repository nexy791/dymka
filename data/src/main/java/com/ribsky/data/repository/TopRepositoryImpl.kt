package com.ribsky.data.repository

import com.ribsky.data.mapper.top.TopMapper
import com.ribsky.data.model.TopApiModel
import com.ribsky.data.model.UserApiModel
import com.ribsky.data.service.offline.top.TopDao
import com.ribsky.data.service.online.top.TopService
import com.ribsky.data.service.user.UserService
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.repository.TopRepository

class TopRepositoryImpl(
    private val topService: TopService,
    private val topDao: TopDao,
    private val topMapper: TopMapper,
    private val userService: UserService,
) : TopRepository {

    private var isNeedToLoadDownUsers = false

    override suspend fun loadUsers(): Result<List<BaseTopModel>> {
        val result = topService.loadUsers()
        result.onSuccess {
            isNeedToLoadDownUsers = true
            topDao.insert(it)
        }
        return result.mapCatching { list -> list.map { topMapper.map(it) } }
    }

    override suspend fun getUsersByScore(): List<BaseTopModel> {
        val list = topDao.get().filter { it.type == TopApiModel.Type.TEST }
        return list.map { topMapper.map(it) }.sortedByDescending { it.score }
    }

    override suspend fun getUsersByStreak(): List<BaseTopModel> {
        val list = topDao.get().filter { it.type == TopApiModel.Type.STREAK }
        return list.map { topMapper.map(it) }.sortedByDescending { it.streak }
    }

    override suspend fun getUsersByPremium(): List<BaseTopModel> {
        val list = topDao.get().filter { it.type == TopApiModel.Type.PREMIUM }
        return list.map { topMapper.map(it) }.shuffled()
    }

    override suspend fun getUsersByStars(): List<BaseTopModel> {
        val list = topDao.get().filter { it.type == TopApiModel.Type.STAR }
        return list.map { topMapper.map(it) }.sortedByDescending { it.starsCount }
    }

    override suspend fun getUsersForUpByTests(): List<BaseTopModel> {
        val user = userService.getUserFromCache() ?: return emptyList()

        val listOfUsers = getUsersByType(TopApiModel.Type.NEAR_TEST)

        val userPosition = listOfUsers.indexOfFirst { it.checkIsUser(user) }
        if (userPosition == -1) return emptyList()

        topDao._insert(
            listOfUsers.map {
                if (it.checkIsUser(user)) it.copy(score = user.score)
                else it
            }
        )

        val nextUser = listOfUsers.getOrNull(userPosition - 1) ?: return emptyList()

        val result = (user.score > nextUser.score)
        return if (result) {

            val listOfUsersNew = getUsersByType(TopApiModel.Type.NEAR_TEST)

            val userPositionNew =
                listOfUsersNew.indexOfFirst { it.checkIsUser(user) }

            val futureUser = listOfUsersNew.getOrNull(userPositionNew - 1)

            val list = mutableListOf<BaseTopModel>().apply {
                add(topMapper.map(TopApiModel.fromUserApi(user, TopApiModel.Type.NEAR_TEST)))
                add(topMapper.map(nextUser))
                futureUser?.let { add(topMapper.map(it)) }
            }
            list
        } else {
            emptyList()
        }

    }

    override suspend fun getUsersForUpByStars(): List<BaseTopModel> {
        val user = userService.getUserFromCache() ?: return emptyList()

        val listOfUsers = getUsersByType(TopApiModel.Type.NEAR_STARS)

        val userPosition = listOfUsers.indexOfFirst { it.checkIsUser(user) }
        if (userPosition == -1) return emptyList()

        topDao._insert(
            listOfUsers.map {
                if (it.checkIsUser(user)) it.copy(starsCount = user.starsCount)
                else it
            }
        )

        val nextUser = listOfUsers.getOrNull(userPosition - 1) ?: return emptyList()

        val result = (user.starsCount > nextUser.starsCount)
        return if (result) {

            val listOfUsersNew = getUsersByType(TopApiModel.Type.NEAR_STARS)

            val userPositionNew =
                listOfUsersNew.indexOfFirst { it.checkIsUser(user) }

            val futureUser = listOfUsersNew.getOrNull(userPositionNew - 1)

            val list = mutableListOf<BaseTopModel>().apply {
                add(topMapper.map(TopApiModel.fromUserApi(user, TopApiModel.Type.NEAR_STARS)))
                add(topMapper.map(nextUser))
                futureUser?.let { add(topMapper.map(it)) }
            }
            list
        } else {
            emptyList()
        }
    }

    override suspend fun getUsersForMoreUpByTests(): List<BaseTopModel> {
        val user = userService.getUserFromCache() ?: return emptyList()
        val listOfUsers = getUsersByType(TopApiModel.Type.NEAR_TEST)

        val userPosition = listOfUsers.indexOfFirst { it.checkIsUser(user) }
        if (userPosition == -1) return emptyList()

        val nextUser = listOfUsers.getOrNull(userPosition - 1) ?: return emptyList()
        val prevUser = listOfUsers.getOrNull(userPosition + 1)
        val result = (user.score < nextUser.score)

        return if (result) {
            val list = mutableListOf<BaseTopModel>().apply {
                add(topMapper.map(TopApiModel.fromUserApi(user, TopApiModel.Type.NEAR_TEST)))
                add(topMapper.map(nextUser))
                prevUser?.let { if (it.score != user.score) add(topMapper.map(it)) }
            }
            return list
        } else {
            emptyList()
        }
    }

    override suspend fun getUsersForMoreUpByStars(): List<BaseTopModel> {
        val user = userService.getUserFromCache() ?: return emptyList()
        val listOfUsers = getUsersByType(TopApiModel.Type.NEAR_STARS)

        val userPosition = listOfUsers.indexOfFirst { it.checkIsUser(user) }
        if (userPosition == -1) return emptyList()

        val nextUser = listOfUsers.getOrNull(userPosition - 1) ?: return emptyList()
        val prevUser = listOfUsers.getOrNull(userPosition + 1)
        val result = (user.starsCount < nextUser.starsCount)

        return if (result) {
            val list = mutableListOf<BaseTopModel>().apply {
                add(topMapper.map(TopApiModel.fromUserApi(user, TopApiModel.Type.NEAR_STARS)))
                add(topMapper.map(nextUser))
                prevUser?.let { if (it.starsCount != user.starsCount) add(topMapper.map(it)) }
            }
            return list
        } else {
            emptyList()
        }
    }

    override suspend fun getUsersForDownByTests(): List<BaseTopModel> {
        if (!isNeedToLoadDownUsers) return emptyList()
        val user = userService.getUserFromCache() ?: return emptyList()
        val listOfUsers = getUsersByType(TopApiModel.Type.NEAR_TEST)

        val userPosition = listOfUsers.indexOfFirst { it.checkIsUser(user) }
        if (userPosition == -1) return emptyList()

        val nextUser = listOfUsers.getOrNull(userPosition - 1) ?: return emptyList()
        val futureUser = listOfUsers.getOrNull(userPosition - 2)
        val result = (user.score < nextUser.score)

        return if (result) {
            val list = mutableListOf<BaseTopModel>().apply {
                add(topMapper.map(TopApiModel.fromUserApi(user, TopApiModel.Type.NEAR_TEST)))
                add(topMapper.map(nextUser))
                futureUser?.let { add(topMapper.map(it)) }
            }
            isNeedToLoadDownUsers = false
            return list
        } else {
            emptyList()
        }
    }

    override suspend fun getUsersForDownByStars(): List<BaseTopModel> {
        if (!isNeedToLoadDownUsers) return emptyList()
        val user = userService.getUserFromCache() ?: return emptyList()
        val listOfUsers = getUsersByType(TopApiModel.Type.NEAR_STARS)

        val userPosition = listOfUsers.indexOfFirst { it.checkIsUser(user) }
        if (userPosition == -1) return emptyList()

        val nextUser = listOfUsers.getOrNull(userPosition - 1) ?: return emptyList()
        val futureUser = listOfUsers.getOrNull(userPosition - 2)
        val result = (user.starsCount < nextUser.starsCount)

        return if (result) {
            val list = mutableListOf<BaseTopModel>().apply {
                add(topMapper.map(TopApiModel.fromUserApi(user, TopApiModel.Type.NEAR_STARS)))
                add(topMapper.map(nextUser))
                futureUser?.let { add(topMapper.map(it)) }
            }
            isNeedToLoadDownUsers = false
            return list
        } else {
            emptyList()
        }
    }


    override suspend fun getUser(id: Int): BaseTopModel = topMapper.map(topDao.get(id))

    override suspend fun isNotEmpty(): Boolean = topDao.getFirst() != null


    private suspend fun getUsersByType(type: TopApiModel.Type): List<TopApiModel> {
        val list = topDao.get().filter { it.type == type }
        return list.sortedByDescending {
            when (type) {
                TopApiModel.Type.TEST -> it.score
                TopApiModel.Type.LESSON -> it.starsCount
                TopApiModel.Type.STREAK -> it.streak
                TopApiModel.Type.STAR -> it.starsCount
                TopApiModel.Type.NEAR_STARS -> it.starsCount
                TopApiModel.Type.NEAR_TEST -> it.score
                TopApiModel.Type.PREMIUM -> TODO()
            }
        }
    }

    private fun TopApiModel.checkIsUser(user: UserApiModel): Boolean =
        this.name == user.name && this.image == user.image
}
