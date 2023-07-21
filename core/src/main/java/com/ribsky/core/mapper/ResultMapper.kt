package com.ribsky.core.mapper

import com.ribsky.core.Resource

class ResultMapper {
    companion object {
        fun <T> Result<T>.asResource(): Resource<T> {
            return this.fold(
                onSuccess = { Resource.success(it) },
                onFailure = { Resource.error(it) }
            )
        }
    }
}
