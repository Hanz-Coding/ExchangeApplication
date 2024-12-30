package com.plcoding.goldchart.gold.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import com.plcoding.goldchart.gold.data.mappers.toCurrency
import com.plcoding.goldchart.gold.data.network.retrofit.SJCAssetApi
import com.plcoding.goldchart.gold.domain.datasource.SJCAssetsDataSource

class RemoteSJCAssetsDataSource(private val api: SJCAssetApi) : SJCAssetsDataSource {

    override suspend fun fetchSJCGoldCurrency(): Result<RemoteCurrency, DataError.RemoteError> {
        return safeCallRetrofit<SJCAssetResponseDto> {
            api.getAssetSJC()
        }.map { response -> response.toCurrency() }
    }
}