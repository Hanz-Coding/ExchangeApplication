package com.plcoding.goldchart.core.data.networking

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import retrofit2.Response
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCallRetrofit(
    execute: () -> Response<T>,
): Result<T, DataError.RemoteError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(DataError.RemoteError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(DataError.RemoteError.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError.RemoteError.UNKNOWN)
    }

    return responseToResultRetrofit(response)
}