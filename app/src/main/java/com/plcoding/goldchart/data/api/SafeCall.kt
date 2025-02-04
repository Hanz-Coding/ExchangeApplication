package com.plcoding.goldchart.data.api

import com.plcoding.goldchart.domain.utils.ResultWrapper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend CoroutineScope.() -> T,
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(call())
        } catch (e: CancellationException) {
            Timber.e(e)
            ResultWrapper.Error(e)
        } catch (e: Throwable) {
            Timber.e(e)
            ResultWrapper.Error(e)
        }
    }
}