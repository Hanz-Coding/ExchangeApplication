package com.plcoding.goldchart.gold.domain.datasource

import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result

interface PNJAssetsDataSource {
    suspend fun fetchPNJGoldCurrency(): Result<RemoteCurrency, DataError.RemoteError>
}