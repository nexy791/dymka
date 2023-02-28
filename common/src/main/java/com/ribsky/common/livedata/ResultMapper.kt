package com.ribsky.common.livedata

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
