package com.plcoding.goldchart.gold.data.repository

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.data.repository.RepositoryImpl
import com.plcoding.goldchart.domain.datasource.CurrencyDataSource
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.gold.data.network.RemotePNJAssetsDataSource
import com.plcoding.goldchart.gold.data.network.RemoteSJCAssetsDataSource
import com.plcoding.goldchart.gold.domain.CompanyName
import java.time.LocalDate

class GoldRepositoryImpl(
    currencyDAO: CurrencyDAO,
    private val remoteSJCDataSource: RemoteSJCAssetsDataSource,
    private val remotePNJDataSource: RemotePNJAssetsDataSource,
) : RepositoryImpl(currencyDAO) {

    override suspend fun fetchCurrency(
        companyName: String,
        date: LocalDate?,
    ): Result<Currency, DataError.RemoteError> {
        val dataSource = getDataSource(companyName)
        return date?.let {
            if (it == LocalDate.now()) {
                dataSource.fetchCurrency()
            } else {
                dataSource.fetchCurrency(date)
            }
        } ?: dataSource.fetchCurrency()
    }

    private fun getDataSource(companyName: String): CurrencyDataSource {
        return when (companyName) {
            CompanyName.SJC -> remoteSJCDataSource
            CompanyName.PNJ -> remotePNJDataSource
            else -> throw IllegalArgumentException("Invalid company name")
        }
    }

    override suspend fun fetchCurrencyHistoryByCode(
        companyName: String,
        code: Int,
    ): Result<Currency, DataError.RemoteError> {
        val dataSource = getDataSource(companyName)
        return dataSource.fetCurrencyHistory(code)
    }
}