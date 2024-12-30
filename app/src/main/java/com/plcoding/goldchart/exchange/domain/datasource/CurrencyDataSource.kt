package com.plcoding.goldchart.exchange.domain.datasource

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency

interface CurrencyDataSource {
    suspend fun fetchCurrency(date: String): Result<RemoteCurrency, DataError.RemoteError>
}