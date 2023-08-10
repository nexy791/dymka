package com.ribsky.domain.usecase.top

import com.ribsky.domain.model.top.BaseTopModel
import com.ribsky.domain.repository.TopRepository

interface TopInteractor {

    suspend fun loadUsersByScore(): List<BaseTopModel>
    suspend fun loadUsersByStreak(): List<BaseTopModel>
    suspend fun loadUsersByPremium(): List<BaseTopModel>
    suspend fun loadUsersByStars(): List<BaseTopModel>
    suspend fun getPlayer(id: Int): BaseTopModel
    suspend fun getUsersForUpByStars(): List<BaseTopModel>
    suspend fun getUsersForUpByTests(): List<BaseTopModel>
    suspend fun getUsersForMoreUpByStars(): List<BaseTopModel>
    suspend fun getUsersForMoreUpByTests(): List<BaseTopModel>

    suspend fun getUsersForDownByStars(): List<BaseTopModel>

    suspend fun getUsersForDownByTests(): List<BaseTopModel>
}

class TopInteractorImpl(private val repository: TopRepository) : TopInteractor {

    override suspend fun loadUsersByScore(): List<BaseTopModel> = repository.getUsersByScore()
    override suspend fun loadUsersByStreak(): List<BaseTopModel> = repository.getUsersByStreak()
    override suspend fun loadUsersByPremium(): List<BaseTopModel> = repository.getUsersByPremium()
    override suspend fun loadUsersByStars(): List<BaseTopModel> = repository.getUsersByStars()

    override suspend fun getPlayer(id: Int): BaseTopModel = repository.getUser(id)
    override suspend fun getUsersForUpByStars(): List<BaseTopModel> =
        repository.getUsersForUpByStars()

    override suspend fun getUsersForUpByTests(): List<BaseTopModel> =
        repository.getUsersForUpByTests()

    override suspend fun getUsersForMoreUpByStars(): List<BaseTopModel> =
        repository.getUsersForMoreUpByStars()

    override suspend fun getUsersForMoreUpByTests(): List<BaseTopModel> =
        repository.getUsersForMoreUpByTests()

    override suspend fun getUsersForDownByStars(): List<BaseTopModel> =
        repository.getUsersForDownByStars()

    override suspend fun getUsersForDownByTests(): List<BaseTopModel> =
        repository.getUsersForDownByTests()

}
