package com.ribsky.data.service.online.test

import com.ribsky.data.model.TestApiModel

interface TestService {

    suspend fun loadTests(): Result<List<TestApiModel>>
}
