package com.plcoding.goldchart.gold.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.domain.datasource.CurrencyDataSource
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCGoldHistoryResponseDto
import com.plcoding.goldchart.gold.data.mappers.toDomain
import com.plcoding.goldchart.gold.data.network.api.SJCAssetApi
import com.plcoding.goldchart.gold.data.network.method.SJCBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RemoteSJCAssetsDataSource(
    private val api: SJCAssetApi,
) : CurrencyDataSource {
    override suspend fun fetchCurrency(date: LocalDate): Result<Currency, DataError.RemoteError> {
        // Chuyển đổi LocalDate sang chuỗi
        val formattedDate = date.format(dateFormater)
        val sjcBody = SJCBody(
            method = "GetSJCGoldPriceByDate",
            toDate = formattedDate
        )
        return safeCallRetrofit<SJCAssetResponseDto> {
            api.getAssetSJCField(sjcBody.method, sjcBody.toDate)
        }.map { response -> response.toDomain() }
    }

    override suspend fun fetchCurrency(): Result<Currency, DataError.RemoteError> {
        return safeCallRetrofit<SJCAssetResponseDto> {
            api.getAssetSJC()
        }.map { response -> response.toDomain() }
    }

    override suspend fun fetCurrencyHistory(code: Int): Result<Currency, DataError.RemoteError> {
        val dateNow = LocalDate.now()
        val toDate = dateNow.format(dateFormater)
        val fromDate = dateNow.minusDays(1).format(dateFormater)
        return safeCallRetrofit<SJCGoldHistoryResponseDto> {
            api.getAssetSJCHistory(
                fromDate = fromDate,
                id = code,
                toDate = toDate
            )
        }.map { response -> response.toDomain() }

    }

    val dateFormater = DateTimeFormatter.ofPattern("dd/MM/yyyy")
}