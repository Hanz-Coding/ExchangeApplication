package com.plcoding.goldchart.gold.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import com.plcoding.goldchart.gold.data.mappers.toDomain
import com.plcoding.goldchart.gold.data.network.api.SJCAssetApi
import com.plcoding.goldchart.gold.data.network.method.SJCBody
import com.plcoding.goldchart.gold.domain.datasource.AssetsDataSource
import com.plcoding.goldchart.gold.domain.model.local.Currency
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RemoteSJCAssetsDataSource(
    private val api: SJCAssetApi,
) : AssetsDataSource {
    override suspend fun fetchAssets(date: LocalDate): Result<Currency, DataError.RemoteError> {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        // Chuyển đổi LocalDate sang chuỗi
        val formattedDate = date.format(formatter)
        println("hanz formattedDate $formattedDate")
        val sjcBody = SJCBody(
            method = "GetSJCGoldPriceByDate",
            toDate = formattedDate
        )
        return safeCallRetrofit<SJCAssetResponseDto> {
            api.getAssetSJCField(sjcBody.method, sjcBody.toDate)
        }.map { response -> response.toDomain() }
    }

    override suspend fun fetchAssets(): Result<Currency, DataError.RemoteError> {
        return safeCallRetrofit<SJCAssetResponseDto> {
            api.getAssetSJC()
        }.map { response -> response.toDomain() }
    }
}