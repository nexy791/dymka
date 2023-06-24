package com.ribsky.data.repository

import com.ribsky.data.mapper.top.TopMapper
import com.ribsky.data.model.TopApiModel
import com.ribsky.data.service.offline.top.TopDao
import com.ribsky.data.service.online.top.TopService
import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.repository.TopRepository

class TopRepositoryImpl(
    private val topService: TopService,
    private val topDao: TopDao,
    private val topMapper: TopMapper,
) : TopRepository {

    override suspend fun loadUsers(): Result<List<BaseTopModel>> {
        val result = topService.loadUsers()
        result.onSuccess {
            topDao.insert(it)
        }
        return result.mapCatching { list -> list.map { topMapper.map(it) } }
    }

    override suspend fun getUsersByScore(): List<BaseTopModel> {
        val list = topDao.get().filter { it.type == TopApiModel.Type.TEST }
        return list.map { topMapper.map(it) }.sortedByDescending { it.score }
    }

    override suspend fun getUsersByLessons(): List<BaseTopModel> {
        val list = topDao.get().filter { it.type == TopApiModel.Type.LESSON }
        return list.map { topMapper.map(it) }.sortedByDescending { it.lessonsCount }
    }

    override suspend fun getUsersByStreak(): List<BaseTopModel> {
        val list = topDao.get().filter { it.type == TopApiModel.Type.STREAK }
        return list.map { topMapper.map(it) }.sortedByDescending { it.streak }
    }

    override suspend fun getUser(id: Int): BaseTopModel = topMapper.map(topDao.get(id))

    override suspend fun isNotEmpty(): Boolean = topDao.getFirst() != null
}
