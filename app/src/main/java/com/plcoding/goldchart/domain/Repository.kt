package com.plcoding.goldchart.domain

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.domain.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface Repository {
    suspend fun saveExchangeToDB(exchangeList: List<Exchange>)
    suspend fun saveCompanyToDB(company: Company)
    suspend fun saveCurrencyToDB(currency: Currency)
    suspend fun getCompany(companyName: String): Company?
    suspend fun getCurrency(companyName: String): Currency?
    fun getCurrencyByCompany(companyName: String): Flow<Currency>
    suspend fun fetchCurrency(
        companyName: String,
        date: LocalDate? = null,
    ): Result<Currency, DataError.RemoteError>

    suspend fun fetchCurrencyHistoryByCode(
        companyName: String,
        code: Int,
    ): Result<Currency, DataError.RemoteError>

    suspend fun fetchAndSaveCurrency(companyName: String)
}