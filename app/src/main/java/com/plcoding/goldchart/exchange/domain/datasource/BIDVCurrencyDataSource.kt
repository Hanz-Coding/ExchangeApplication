package com.plcoding.goldchart.exchange.domain.datasource

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency

interface BIDVCurrencyDataSource {
    suspend fun fetchBIDVCurrency(): Result<RemoteCurrency, DataError.RemoteError>
}