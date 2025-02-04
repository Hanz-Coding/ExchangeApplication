package com.plcoding.goldchart.exchange.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.domain.datasource.CurrencyDataSource
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.mappers.toDomain
import com.plcoding.goldchart.exchange.data.network.api.BIDVCurrencyApi
import java.time.LocalDate

class RemoteBIDVCurrencyDatasource(val api: BIDVCurrencyApi) : CurrencyDataSource {

    override suspend fun fetchCurrency(date: LocalDate): Result<Currency, DataError.RemoteError> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCurrency(): Result<Currency, DataError.RemoteError> {
        return safeCallRetrofit<BIDVCurrencyResponseDto> {
            api.fetchCurrency()
        }.map { responseDto ->
            responseDto.toDomain()
        }
    }

    override suspend fun fetCurrencyHistory(code: Int): Result<Currency, DataError.RemoteError> {
        TODO("Not yet implemented")
    }
}