package com.plcoding.goldchart.exchange.domain.datasource

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency

interface VCBCurrencyDataSource {
    suspend fun fetchVCBCurrency(date: String): Result<RemoteCurrency, DataError.RemoteError>

}