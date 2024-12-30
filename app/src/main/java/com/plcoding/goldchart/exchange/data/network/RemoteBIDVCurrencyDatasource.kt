package com.plcoding.goldchart.exchange.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.mappers.toCurrency
import com.plcoding.goldchart.exchange.data.network.api.BIDVCurrencyApi
import com.plcoding.goldchart.exchange.domain.datasource.BIDVCurrencyDataSource
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency

class RemoteBIDVCurrencyDatasource(val api: BIDVCurrencyApi) : BIDVCurrencyDataSource {
    override suspend fun fetchBIDVCurrency(): Result<RemoteCurrency, DataError.RemoteError> {
        return safeCallRetrofit<BIDVCurrencyResponseDto> {
            api.fetchBIDVCurrency()
        }.map { responseDto ->
            responseDto.toCurrency()
        }
    }
}