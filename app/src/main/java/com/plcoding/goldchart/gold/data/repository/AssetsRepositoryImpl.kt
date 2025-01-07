package com.plcoding.goldchart.gold.data.repository

import com.plcoding.goldchart.core.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.gold.data.mappers.toDomain
import com.plcoding.goldchart.gold.data.mappers.toEntity
import com.plcoding.goldchart.gold.data.network.RemotePNJAssetsDataSource
import com.plcoding.goldchart.gold.data.network.RemoteSJCAssetsDataSource
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.domain.model.local.Currency
import com.plcoding.goldchart.gold.domain.model.local.CurrencyCompany
import com.plcoding.goldchart.gold.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.gold.domain.repository.AssetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class AssetsRepositoryImpl(
    private val currencyDAO: CurrencyDAO,
    private val remoteSJCDataSource: RemoteSJCAssetsDataSource,
    private val remotePNJDataSource: RemotePNJAssetsDataSource,
) : AssetsRepository {
    override suspend fun saveExchangeToDB(exchangeList: List<CurrencyExchange>) {
        currencyDAO.upsertExchangeList(exchangeList.map { it.toEntity() })
    }

    override suspend fun saveCompanyToDB(company: CurrencyCompany) {
        currencyDAO.upsertCompany(company.toEntity())
    }

    override suspend fun getCompanyByName(companyName: String): CurrencyCompany? {
        return currencyDAO.getCompanyByName(companyName)?.toDomain()
    }

    override fun getCurrencyByCompanyName(companyName: String): Flow<Currency> {
        return currencyDAO.getCurrencyByCompanyName(companyName).filterNotNull().map {
            it.toDomain()
        }
    }

    override suspend fun fetchCurrencyByName(
        companyName: String,
        date: LocalDate,
    ): Result<Currency, DataError.RemoteError> {
        return when (companyName) {
            CompanyName.SJC -> remoteSJCDataSource.fetchAssets(date)
            CompanyName.PNJ -> remotePNJDataSource.fetchAssets()
            else -> throw IllegalArgumentException("Invalid company name")
        }
    }

    override suspend fun fetchCurrencyByName(companyName: String): Result<Currency, DataError.RemoteError> {
        return when (companyName) {
            CompanyName.SJC -> remoteSJCDataSource.fetchAssets()
            CompanyName.PNJ -> remotePNJDataSource.fetchAssets()
            else -> throw IllegalArgumentException("Invalid company name")
        }
    }
}