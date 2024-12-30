package com.plcoding.goldchart.exchange.domain

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.model.Currency
import com.plcoding.goldchart.core.domain.model.CurrencyExchange
import com.plcoding.goldchart.core.domain.model.CurrencyCompany
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun saveExchangeToDB(exchangeList: List<CurrencyExchange>)
    suspend fun saveCompanyToDB(company: CurrencyCompany)
    suspend fun getCompanyByName(companyName: String): CurrencyCompany?
    fun getCurrencyByCompanyName(companyName: String): Flow<Currency>
    suspend fun fetchCurrencyByName(companyName: String): Result<RemoteCurrency, DataError.RemoteError>
}