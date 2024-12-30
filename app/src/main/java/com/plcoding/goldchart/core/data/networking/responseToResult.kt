package com.plcoding.goldchart.core.data.networking

import com.plcoding.goldchart.core.domain.utils.DataError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import com.plcoding.goldchart.core.domain.utils.Result
import retrofit2.Response

suspend inline fun <reified T> responseToResult(response: HttpResponse)
        : Result<T, DataError.RemoteError> {
    return when (response.status.value) {
        in 200..299 ->
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(DataError.RemoteError.SERIALIZATION_ERROR)
            }

        408 -> Result.Error(DataError.RemoteError.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.RemoteError.TOO_MANY_REQUEST)
        in 500..599 -> Result.Error(DataError.RemoteError.SERVER_ERROR)
        else -> Result.Error(DataError.RemoteError.UNKNOWN)
    }
}

suspend inline fun <reified T> responseToResultRetrofit(response: Response<T>)
        : Result<T, DataError.RemoteError> {
    return when (response.code()) {
        in 200..299 ->
            try {
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(DataError.RemoteError.SERIALIZATION_ERROR)
                }
            } catch (e: NoTransformationFoundException) {
                Result.Error(DataError.RemoteError.SERIALIZATION_ERROR)
            }

        408 -> Result.Error(DataError.RemoteError.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.RemoteError.TOO_MANY_REQUEST)
        in 500..599 -> Result.Error(DataError.RemoteError.SERVER_ERROR)
        else -> Result.Error(DataError.RemoteError.UNKNOWN)
    }
}