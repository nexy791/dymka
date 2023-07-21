package com.ribsky.core

data class Resource<out T>(val status: Status, val data: T?, val exception: Throwable?) {
    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
    }

    val isLoading: Boolean
        get() = status == Status.LOADING

    val isSuccess: Boolean get() = status == Status.SUCCESS

    val isError: Boolean get() = status == Status.ERROR

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Throwable): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
