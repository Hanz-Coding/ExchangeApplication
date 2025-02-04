package com.plcoding.goldchart.domain.datasource

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.domain.model.Currency
import java.time.LocalDate

interface CurrencyDataSource {
    suspend fun fetchCurrency(date: LocalDate): Result<Currency, DataError.RemoteError>
    suspend fun fetchCurrency(): Result<Currency, DataError.RemoteError>
    suspend fun fetCurrencyHistory(code: Int): Result<Currency, DataError.RemoteError>
}