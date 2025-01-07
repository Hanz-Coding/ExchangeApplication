package com.plcoding.goldchart.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.goldchart.core.domain.utils.onSuccess
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.domain.model.local.Currency
import com.plcoding.goldchart.gold.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.gold.domain.repository.AssetsRepository
import com.plcoding.goldchart.gold.presentation.mappers.toUI
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class AssetsViewModel(
    private val repository: AssetsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(GoldState())
    val state = _state.onStart {
        loadLocalAssets(CompanyName.SJC)
        fetchAndSaveCurrencyDB(CompanyName.SJC)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), GoldState()
    )

    private fun fetchAndSaveCurrencyDB(companyName: String) {
        viewModelScope.launch {
            val localCurrency = getLocalCurrency(companyName)
            val remoteCurrency = fetchDataAndCombine(companyName)
            remoteCurrency?.let {
                val needUpdateDB = needUpdateDB(localCurrency, it)
                if (needUpdateDB) {
                    saveDB(remoteCurrency)
                }
            }
        }
    }

    suspend fun fetchDataAndCombine(companyName: String): Currency? {
        // Khởi tạo coroutine scope
        return coroutineScope {
            // Thực hiện fetch đồng thời cả hai API
            val api1Result = async { repository.fetchCurrencyByName(companyName) }
            val api2Result =
                async { repository.fetchCurrencyByName(companyName, LocalDate.now().minusDays(2)) }

            // Chờ kết quả từ cả hai API và gộp lại
            val result1 = api1Result.await()
            val result2 = api2Result.await()  // Chờ kết quả từ API 2

            // Gộp kết quả của cả hai API thành một đối tượng CombinedResult
            var currentCurrency: Currency? = null
            result1.onSuccess {
                currentCurrency = it
            }

            var previourCurrency: Currency? = null
            result2.onSuccess {
                previourCurrency = it
            }

            currentCurrency?.let { current ->
                val company = current.company
                previourCurrency?.let { prev ->
                    val exchangeUI = current.exchangeList.zip(prev.exchangeList) { current, prev ->
                        CurrencyExchange(
                            currencyCode = current.currencyCode,
                            currencyName = current.currencyName,
                            companyName = current.companyName,
                            currencyType = current.currencyType,
                            iconUrl = current.iconUrl,
                            buy = current.buy,
                            transfer = current.transfer,
                            sell = current.sell,
                            previousBuy = prev.buy,
                            previousTransfer = prev.transfer,
                            previousSell = prev.sell
                        )
                    }
                    return@coroutineScope Currency(
                        company = company, exchangeList = exchangeUI
                    ).also {
                        println("hanz exchangeList $exchangeUI")
                    }
                }
            }
            return@coroutineScope null
        }
    }

    private fun loadLocalAssets(companyName: String) {
        viewModelScope.launch {
            repository.getCurrencyByCompanyName(companyName).collect { currency ->
                val currencyUI = currency.toUI()
                _state.update {
                    val updatedCurrency = it.currencyMap.toMutableMap()
                    updatedCurrency[CompanyName.SJC] = currencyUI
                    it.copy(currencyMap = updatedCurrency)
                }
            }
        }
    }

    private suspend fun getLocalCurrency(companyName: String): Currency? {
        val localCompany = repository.getCompanyByName(companyName)
        return if (localCompany == null) {
            null
        } else {
            repository.getCurrencyByCompanyName(companyName).first()
        }
    }

    private suspend fun saveDB(currency: com.plcoding.goldchart.gold.domain.model.local.Currency) {
        repository.saveCompanyToDB(currency.company)
        repository.saveExchangeToDB(currency.exchangeList)
    }

    private fun needUpdateDB(localCurrency: Currency?, remoteCurrency: Currency): Boolean {
        return localCurrency == null || remoteCurrency.company.updatedTime - localCurrency.company.updatedTime != 0L
    }

    //    private fun generatedNewCurrency(
//        currency: Currency?,
//        remoteCurrency: Currency,
//    ): Currency {
//        return Currency(
//            generateNewCompany(currency, remoteCurrency),
//            generateNewExchangeList(currency, remoteCurrency)
//        )
//    }
//
//    private fun generateNewCompany(
//        localCurrency: Currency?,
//        remoteCurrency: Currency,
//    ): Company {
//        return Company(
//            name = remoteCurrency.company.name,
//            updatedTime = remoteCurrency.company.updatedTime
//        )
//    }
//
    private fun generateNewExchangeList(
        localCurrency: Currency?,
        remoteCurrency: Currency,
    ): List<CurrencyExchange> {
        // khi database chưa có dữ liệu
        val currency = localCurrency ?: remoteCurrency
        val localExchangeList = currency.exchangeList
        val remoteExchangeList = remoteCurrency.exchangeList
        return localExchangeList.zip(remoteExchangeList) { exchange, remote ->
            CurrencyExchange(
                currencyCode = exchange.currencyCode,
                currencyName = exchange.currencyName,
                companyName = exchange.companyName,
                currencyType = exchange.currencyType,
                iconUrl = exchange.iconUrl,
                buy = remote.buy,
                transfer = remote.transfer,
                sell = remote.sell,
                previousBuy = exchange.buy,
                previousTransfer = exchange.transfer,
                previousSell = exchange.sell
            )
        }
    }

}