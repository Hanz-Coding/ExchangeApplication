package com.plcoding.goldchart.exchange.data.repository

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.data.repository.RepositoryImpl
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.exchange.data.network.RemoteBIDVCurrencyDatasource
import com.plcoding.goldchart.exchange.data.network.RemoteVCBCurrencyDatasource
import com.plcoding.goldchart.exchange.domain.CompanyName
import java.time.LocalDate

class ExchangeRepositoryImpl(
    currencyDAO: CurrencyDAO,
    private val remoteVCBDataSource: RemoteVCBCurrencyDatasource,
    private val remoteBIDVDataSource: RemoteBIDVCurrencyDatasource,
) : RepositoryImpl(currencyDAO) {

    override suspend fun fetchCurrency(
        companyName: String,
        date: LocalDate?,
    ): Result<Currency, DataError.RemoteError> {
        val dataSource = when (companyName) {
            CompanyName.VCB -> remoteVCBDataSource
            CompanyName.BIDV -> remoteBIDVDataSource
            else -> throw IllegalArgumentException("Invalid company name")
        }
        return date?.let { dataSource.fetchCurrency(date) }
            ?: dataSource.fetchCurrency()
    }

    override suspend fun fetchCurrencyHistoryByCode(
        companyName: String,
        code: Int,
    ): Result<Currency, DataError.RemoteError> {
        TODO("Not yet implemented")
    }
}