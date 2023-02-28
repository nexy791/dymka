package com.ribsky.data.service.online.top

import com.ribsky.data.model.TopApiModel

interface TopService {

    suspend fun loadUsers(): Result<List<TopApiModel>>
}
