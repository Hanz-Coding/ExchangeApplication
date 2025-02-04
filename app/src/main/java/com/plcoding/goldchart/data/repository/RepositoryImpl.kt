package com.plcoding.goldchart.data.repository

import com.plcoding.goldchart.core.domain.utils.onSuccess
import com.plcoding.goldchart.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.data.mappers.toDomain
import com.plcoding.goldchart.data.mappers.toEntity
import com.plcoding.goldchart.domain.Repository
import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.LocalDate

abstract class RepositoryImpl(
    private val currencyDAO: CurrencyDAO,
) : Repository {
    override suspend fun saveExchangeToDB(exchangeList: List<Exchange>) {
        currencyDAO.upsertExchangeList(exchangeList.map { it.toEntity() })
    }

    override suspend fun saveCompanyToDB(company: Company) {
        currencyDAO.upsertCompany(company.toEntity())
    }

    override suspend fun getCompany(companyName: String): Company? {
        return currencyDAO.getCompanyByName(companyName)?.toDomain()
    }

    override suspend fun getCurrency(companyName: String): Currency? {
        return currencyDAO.getCurrency(companyName)?.toDomain()
    }

    override fun getCurrencyByCompany(companyName: String): Flow<Currency> {
        return currencyDAO.getCurrencyByCompanyName(companyName)
            .filterNotNull()
            .map { entity ->
                entity.toDomain()
            }
    }

    override suspend fun fetchAndSaveCurrency(companyName: String) {
        val localCurrency = getCurrency(companyName)
        val remoteCurrency = fetchDataAndCombine(companyName)
        remoteCurrency?.let {
            val needUpdateDB = needUpdateDB(localCurrency, it)
            if (needUpdateDB) {
                saveCurrencyToDB(remoteCurrency)
            }
        }
    }

    private suspend fun fetchDataAndCombine(companyName: String): Currency? {
        // Khởi tạo coroutine scope
        return coroutineScope {
            // Thực hiện fetch đồng thời cả hai API
            val api1Result = async { fetchCurrency(companyName, LocalDate.now()) }
            val api2Result =
                async { fetchCurrency(companyName, LocalDate.now().minusDays(1)) }

            // Chờ kết quả từ cả hai API và gộp lại
            val result1 = api1Result.await()
            val result2 = api2Result.await()  // Chờ kết quả từ API 2

            // Gộp kết quả của cả hai API thành một đối tượng CombinedResult
            var currentCurrency: Currency? = null
            result1.onSuccess {
                currentCurrency = it
            }

            var previousCurrency: Currency? = null
            result2.onSuccess {
                previousCurrency = it
            }

            currentCurrency?.let { current ->
                val company = current.company
                previousCurrency?.let { prev ->
                    val exchangeList =
                        current.exchangeList.zip(prev.exchangeList) { current, previous ->
                            generateExchange(current, previous)
                        }
                    return@coroutineScope Currency(
                        company = company, exchangeList = exchangeList
                    ).also {
                        Timber.d("hanz exchangeList $exchangeList")
                    }
                }
            }
            return@coroutineScope null
        }
    }

    private fun generateExchange(current: Exchange, previous: Exchange): Exchange {
        return Exchange(
            currencyId = current.currencyId,
            currencyCode = current.currencyCode,
            currencyName = current.currencyName,
            companyName = current.companyName,
            currencyType = current.currencyType,
            iconUrl = current.iconUrl,
            buy = current.buy,
            transfer = current.transfer,
            sell = current.sell,
            previousBuy = previous.buy,
            previousTransfer = previous.transfer,
            previousSell = previous.sell
        )
    }

    private fun needUpdateDB(localCurrency: Currency?, remoteCurrency: Currency): Boolean {
        return localCurrency == null || remoteCurrency.company.updatedTime - localCurrency.company.updatedTime != 0L
    }

    override suspend fun saveCurrencyToDB(currency: Currency) {
        saveCompanyToDB(currency.company)
        saveExchangeToDB(currency.exchangeList)
    }
}