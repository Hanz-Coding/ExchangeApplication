package com.plcoding.goldchart.gold.presentation.gold_price

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.goldchart.core.domain.model.Currency
import com.plcoding.goldchart.core.domain.model.CurrencyCompany
import com.plcoding.goldchart.core.domain.model.CurrencyExchange
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.utils.onError
import com.plcoding.goldchart.core.domain.utils.onSuccess
import com.plcoding.goldchart.exchange.presentation.mappers.toDomain
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.domain.repository.AssetsRepository
import com.plcoding.goldchart.gold.presentation.mappers.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GoldPriceViewModel(
    private val repository: AssetsRepository,
) : ViewModel() {

    private val _stateSJC = MutableStateFlow(CategoryState())
    val stateSJC = _stateSJC.onStart {
        loadLocalAssets(CompanyName.SJC)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CategoryState()
    )

    private val _statePNJ = MutableStateFlow(CategoryState())
    val statePNJ = _statePNJ.onStart {
        loadLocalAssets(CompanyName.PNJ)
        fetchAndSaveCurrencyDB(CompanyName.PNJ)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CategoryState()
    )

    private fun loadLocalAssets(companyName: String) {
        viewModelScope.launch {
            repository.getCurrencyByCompanyName(companyName).collect { currency ->
                val currencyUI = currency.toUI()
//                _state.update {
//                    val updatedCurrency = it.currencyMap.toMutableMap()
//                    updatedCurrency[CompanyName.SJC] = currencyUI
//                    it.copy(currencyMap = updatedCurrency)
//                }
            }
        }
    }

    private fun fetchAndSaveCurrencyDB(companyName: String) {
        viewModelScope.launch {
            repository.fetchCurrencyByName(companyName).onSuccess { remoteCurrency ->
                val localCurrency = getLocalCurrency(companyName)
                val needUpdateDB = needUpdateDB(localCurrency, remoteCurrency)
                if (needUpdateDB) {
                    val newCurrency = generatedNewCurrency(localCurrency, remoteCurrency)
                    saveDB(newCurrency)
                }
            }.onError {

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

    private suspend fun saveDB(currency: Currency) {
        repository.saveCompanyToDB(currency.company)
        repository.saveExchangeToDB(currency.exchangeList)
    }

    private fun needUpdateDB(localCurrency: Currency?, remoteCurrency: RemoteCurrency): Boolean {
        return localCurrency == null ||
                remoteCurrency.company.updatedTime - localCurrency.company.updatedTime != 0L
    }

    private fun generatedNewCurrency(
        currency: Currency?,
        remoteCurrency: RemoteCurrency,
    ): Currency {
        return Currency(
            generateNewCompany(currency, remoteCurrency),
            generateNewExchangeList(currency, remoteCurrency)
        )
    }

    private fun generateNewCompany(
        localCurrency: Currency?,
        remoteCurrency: RemoteCurrency,
    ): CurrencyCompany {
        return CurrencyCompany(
            companyName = remoteCurrency.company.companyName,
            updatedTime = remoteCurrency.company.updatedTime
        )
    }

    private fun generateNewExchangeList(
        localCurrency: Currency?,
        remoteCurrency: RemoteCurrency,
    ): List<CurrencyExchange> {
        // khi database chưa có dữ liệu
        val currency = localCurrency ?: remoteCurrency.toDomain()
        val localExchangeList = currency.exchangeList
        val remoteExchangeList = remoteCurrency.exchangeList
        return localExchangeList.zip(remoteExchangeList) { exchange, remote ->
            CurrencyExchange(
                currencyCode = exchange.currencyCode,
                currencyName = exchange.currencyName,
                companyName = exchange.companyName,
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