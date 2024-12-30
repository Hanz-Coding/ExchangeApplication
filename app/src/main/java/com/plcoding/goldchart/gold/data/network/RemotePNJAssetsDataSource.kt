package com.plcoding.goldchart.gold.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetResponseDto
import com.plcoding.goldchart.gold.data.mappers.toCurrency
import com.plcoding.goldchart.gold.data.network.retrofit.PNJAssetApi
import com.plcoding.goldchart.gold.domain.datasource.PNJAssetsDataSource

class RemotePNJAssetsDataSource(private val api: PNJAssetApi) : PNJAssetsDataSource {
    override suspend fun fetchPNJGoldCurrency(): Result<RemoteCurrency, DataError.RemoteError> {
        return safeCallRetrofit<PNJAssetResponseDto> {
            api.fetchPNJAsset()
        }.map { response -> response.toCurrency() }
    }
}