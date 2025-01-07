package com.plcoding.goldchart.gold.domain.datasource

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.gold.domain.model.local.Currency
import java.time.LocalDate

interface AssetsDataSource {
    suspend fun fetchAssets(date: LocalDate): Result<Currency, DataError.RemoteError>
    suspend fun fetchAssets(): Result<Currency, DataError.RemoteError>
}