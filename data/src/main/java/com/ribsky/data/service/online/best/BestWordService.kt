package com.ribsky.data.service.online.best

import com.ribsky.data.model.BestWordApiModel

interface BestWordService {

    suspend fun loadWords(): Result<List<BestWordApiModel>>

    suspend fun getCurrentBestWord(): BestWordApiModel
}
