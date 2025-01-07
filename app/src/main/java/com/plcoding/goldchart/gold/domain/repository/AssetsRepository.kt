package com.plcoding.goldchart.gold.domain.repository

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.gold.domain.model.local.Currency
import com.plcoding.goldchart.gold.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.gold.domain.model.local.CurrencyCompany
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AssetsRepository {
    suspend fun saveExchangeToDB(exchangeList: List<CurrencyExchange>)
    suspend fun saveCompanyToDB(company: CurrencyCompany)
    suspend fun getCompanyByName(companyName: String): CurrencyCompany?
    fun getCurrencyByCompanyName(companyName: String): Flow<Currency>
    suspend fun fetchCurrencyByName(
        companyName: String,
        date: LocalDate,
    ): Result<Currency, DataError.RemoteError>

    suspend fun fetchCurrencyByName(
        companyName: String,
    ): Result<Currency, DataError.RemoteError>
}