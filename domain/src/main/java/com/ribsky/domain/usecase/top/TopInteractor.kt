package com.ribsky.domain.usecase.top

import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.repository.TopRepository

interface TopInteractor {

    suspend fun loadUsersByScore(): List<BaseTopModel>

    suspend fun loadUsersByLessons(): List<BaseTopModel>

    suspend fun loadUsersByStreak(): List<BaseTopModel>

    suspend fun loadUsersByPremium(): List<BaseTopModel>

    suspend fun getPlayer(id: Int): BaseTopModel
}

class TopInteractorImpl(private val repository: TopRepository) : TopInteractor {

    override suspend fun loadUsersByScore(): List<BaseTopModel> = repository.getUsersByScore()
    override suspend fun loadUsersByLessons(): List<BaseTopModel> = repository.getUsersByLessons()
    override suspend fun loadUsersByStreak(): List<BaseTopModel> = repository.getUsersByStreak()
    override suspend fun loadUsersByPremium(): List<BaseTopModel> = repository.getUsersByPremium()

    override suspend fun getPlayer(id: Int): BaseTopModel = repository.getUser(id)
}
