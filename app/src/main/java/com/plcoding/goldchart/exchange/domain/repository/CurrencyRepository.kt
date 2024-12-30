package com.plcoding.goldchart.exchange.domain.repository

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.exchange.domain.model.local.Currency
import com.plcoding.goldchart.exchange.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.exchange.domain.model.local.Company
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun saveExchangeToDB(exchangeList: List<CurrencyExchange>)
    suspend fun saveCompanyToDB(company: Company)
    suspend fun getCompanyByName(companyName: String): Company?
    fun getCurrencyByCompanyName(companyName: String): Flow<Currency>
    suspend fun fetchCurrencyByName(companyName: String): Result<RemoteCurrency, DataError.RemoteError>
}