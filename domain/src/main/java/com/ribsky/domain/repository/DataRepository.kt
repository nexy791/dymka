package com.ribsky.domain.repository

interface DataRepository {

    suspend fun getData(): Result<Unit>
}
