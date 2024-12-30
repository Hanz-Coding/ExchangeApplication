package com.plcoding.goldchart.exchange.data.repository

import com.plcoding.goldchart.core.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.exchange.data.network.RemoteBIDVCurrencyDatasource
import com.plcoding.goldchart.exchange.data.network.RemoteVCBCurrencyDatasource
import com.plcoding.goldchart.exchange.domain.repository.CurrencyRepository
import com.plcoding.goldchart.exchange.domain.getCurrentDate
import com.plcoding.goldchart.exchange.domain.model.local.Currency
import com.plcoding.goldchart.exchange.domain.model.local.Company
import com.plcoding.goldchart.exchange.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.exchange.data.mappers.toDomain
import com.plcoding.goldchart.exchange.data.mappers.toEntity
import com.plcoding.goldchart.exchange.domain.CompanyName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(
    private val currencyDAO: CurrencyDAO,
    private val remoteVCBDataSource: RemoteVCBCurrencyDatasource,
    private val remoteBIDVDataSource: RemoteBIDVCurrencyDatasource,
) : CurrencyRepository {
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
            CompanyName.VCB -> remoteVCBDataSource.fetchCurrency(getCurrentDate())
            CompanyName.BIDV -> remoteBIDVDataSource.fetchCurrency(getCurrentDate())
            else -> throw IllegalArgumentException("Invalid company name")
        }
    }
}