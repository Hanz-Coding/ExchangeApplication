package com.plcoding.goldchart.gold.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetResponseDto
import com.plcoding.goldchart.gold.data.mappers.toDomain
import com.plcoding.goldchart.gold.data.network.api.PNJAssetApi
import com.plcoding.goldchart.gold.domain.datasource.AssetsDataSource
import com.plcoding.goldchart.gold.domain.model.local.Currency
import java.time.LocalDate

class RemotePNJAssetsDataSource(private val api: PNJAssetApi) : AssetsDataSource {
    override suspend fun fetchAssets(date: LocalDate): Result<Currency, DataError.RemoteError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAssets(): Result<Currency, DataError.RemoteError> {
        return safeCallRetrofit<PNJAssetResponseDto> {
            api.fetchPNJAsset()
        }.map { response -> response.toDomain() }
    }
}