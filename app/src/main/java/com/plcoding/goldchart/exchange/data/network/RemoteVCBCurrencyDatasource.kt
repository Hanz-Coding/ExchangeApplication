package com.plcoding.goldchart.exchange.data.network

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.domain.datasource.CurrencyDataSource
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.exchange.data.dto.vcb.VCBCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.mappers.toDomain
import com.plcoding.goldchart.exchange.data.network.api.VCBCurrencyApi
import com.plcoding.goldchart.exchange.domain.getCurrentDate
import java.time.LocalDate

class RemoteVCBCurrencyDatasource(val api: VCBCurrencyApi) : CurrencyDataSource {
    override suspend fun fetchCurrency(date: LocalDate): Result<Currency, DataError.RemoteError> {
        return safeCallRetrofit<VCBCurrencyResponseDto> {
            api.fetchCurrency(getCurrentDate(date)).also {
                println("hanz fetchCurrency vcb date ${getCurrentDate(date)}")
            }
        }.map { responseDto ->
            responseDto.toDomain()
        }
    }

    override suspend fun fetchCurrency(): Result<Currency, DataError.RemoteError> {
        return safeCallRetrofit<VCBCurrencyResponseDto> {
            api.fetchCurrency(getCurrentDate(LocalDate.now())).also {
                println("hanz fetchCurrency vcb date now ${getCurrentDate(LocalDate.now())}")
            }
        }.map { responseDto ->
            responseDto.toDomain()
        }
    }

    override suspend fun fetCurrencyHistory(code: Int): Result<Currency, DataError.RemoteError> {
        TODO("Not yet implemented")
    }
}