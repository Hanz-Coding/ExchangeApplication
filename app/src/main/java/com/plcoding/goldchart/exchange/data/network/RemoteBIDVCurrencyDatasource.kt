package com.plcoding.goldchart.exchange.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.mappers.toDomain
import com.plcoding.goldchart.exchange.data.network.api.BIDVCurrencyApi
import com.plcoding.goldchart.exchange.domain.datasource.CurrencyDataSource
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency

class RemoteBIDVCurrencyDatasource(val api: BIDVCurrencyApi) : CurrencyDataSource {
    override suspend fun fetchCurrency(date: String): Result<RemoteCurrency, DataError.RemoteError> {
        return safeCallRetrofit<BIDVCurrencyResponseDto> {
            api.fetchCurrency()
        }.map { responseDto ->
            responseDto.toDomain()
        }
    }
}