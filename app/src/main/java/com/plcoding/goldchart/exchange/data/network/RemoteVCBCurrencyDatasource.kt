package com.plcoding.goldchart.exchange.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.exchange.data.dto.vcb.VCBCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.mappers.toCurrency
import com.plcoding.goldchart.exchange.data.network.api.VCBCurrencyApi
import com.plcoding.goldchart.exchange.domain.datasource.CurrencyDataSource
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency

class RemoteVCBCurrencyDatasource(val api: VCBCurrencyApi) : CurrencyDataSource {
    override suspend fun fetchCurrency(date: String): Result<RemoteCurrency, DataError.RemoteError> {
        return safeCallRetrofit<VCBCurrencyResponseDto> {
            api.fetchCurrency(date)
        }.map { responseDto ->
            responseDto.toCurrency()
        }
    }
}