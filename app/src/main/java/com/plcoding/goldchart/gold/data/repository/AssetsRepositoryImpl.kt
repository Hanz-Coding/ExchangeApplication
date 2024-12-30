package com.plcoding.goldchart.gold.data.repository

import com.plcoding.goldchart.core.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.exchange.domain.model.local.Currency
import com.plcoding.goldchart.exchange.domain.model.local.Company
import com.plcoding.goldchart.exchange.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.exchange.data.mappers.toDomain
import com.plcoding.goldchart.exchange.data.mappers.toEntity
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.domain.datasource.PNJAssetsDataSource
import com.plcoding.goldchart.gold.domain.datasource.SJCAssetsDataSource
import com.plcoding.goldchart.gold.domain.repository.AssetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class AssetsRepositoryImpl(
    private val currencyDAO: CurrencyDAO,
    private val remoteSJCDataSource: SJCAssetsDataSource,
    private val remotePNJDataSource: PNJAssetsDataSource,
) : AssetsRepository {
    override suspend fun saveExchangeToDB(exchangeList: List<CurrencyExchange>) {
        currencyDAO.upsertExchangeList(exchangeList.map { it.toEntity() })
    }

    override suspend fun saveCompanyToDB(company: Company) {
        currencyDAO.upsertCompany(company.toEntity())
    }

    override suspend fun getCompanyByName(companyName: String): Company? {
        return currencyDAO.getCompanyByName(companyName)?.toDomain()
    }

    override fun getCurrencyByCompanyName(companyName: String): Flow<Currency> {
        return currencyDAO.getCurrencyByCompanyName(companyName).filterNotNull().map {
            it.toDomain()
        }
    }

    override suspend fun fetchCurrencyByName(companyName: String): Result<RemoteCurrency, DataError.RemoteError> {
        return when (companyName) {
            CompanyName.SJC -> remoteSJCDataSource.fetchSJCGoldCurrency()
            CompanyName.PNJ -> remotePNJDataSource.fetchPNJGoldCurrency()
            else -> throw IllegalArgumentException("Invalid company name")
        }
    }
}