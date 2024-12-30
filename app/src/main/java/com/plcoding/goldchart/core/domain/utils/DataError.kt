package com.plcoding.goldchart.core.domain.utils

sealed interface DataError : Error {
    enum class RemoteError : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUEST,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN
    }

    enum class LocalError : DataError {
        DISK_FULL,
        UNKNOWN
    }
}