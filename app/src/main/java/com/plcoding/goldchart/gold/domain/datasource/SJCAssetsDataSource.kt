package com.plcoding.goldchart.gold.domain.datasource

import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result

interface SJCAssetsDataSource {
    suspend fun fetchSJCGoldCurrency(): Result<RemoteCurrency, DataError.RemoteError>
}