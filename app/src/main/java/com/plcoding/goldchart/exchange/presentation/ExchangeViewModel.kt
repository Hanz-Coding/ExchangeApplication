package com.plcoding.goldchart.exchange.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.goldchart.core.domain.utils.onError
import com.plcoding.goldchart.core.domain.utils.onSuccess
import com.plcoding.goldchart.exchange.domain.repository.CurrencyRepository
import com.plcoding.goldchart.exchange.domain.model.local.Currency
import com.plcoding.goldchart.exchange.domain.model.local.Company
import com.plcoding.goldchart.exchange.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.presentation.mappers.toDomain
import com.plcoding.goldchart.exchange.presentation.mappers.toUI
import com.plcoding.goldchart.exchange.presentation.state.ExchangeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExchangeViewModel(private val repository: CurrencyRepository) : ViewModel() {
    private val _state = MutableStateFlow(ExchangeState())
    val state = _state.onStart {
        loadLocalCurrency(CompanyName.VCB)
        fetchAndSaveCurrencyDB(CompanyName.VCB)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ExchangeState()
    )

    fun fetchAndLoadCurrencyBIDV() {
        fetchAndSaveCurrencyDB(CompanyName.BIDV)
        loadLocalCurrency(CompanyName.BIDV)
    }

    fun onPullToRefreshTrigger(companyName: String) {
        _state.update {
            it.copy(isLoading = true).also {
                println("PullToRefreshBox _state.update 1 ${it.isLoading}")
            }
        }
        println("PullToRefreshBox companyName $companyName")
        fetchAndSaveCurrencyDB(companyName)
    }

    private fun loadLocalCurrency(companyName: String) {
        viewModelScope.launch {
            repository.getCurrencyByCompanyName(companyName).collect { currency ->
                val currencyUI = currency.toUI()
                _state.update {
                    val updatedCurrency = it.currencyMap.toMutableMap()
                    updatedCurrency[companyName] = currencyUI
                    it.copy(currencyMap = updatedCurrency)
                }
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

        _state.update {
            it.copy(isLoading = false).also {
                println("PullToRefreshBox _state.update 5 ${it.isLoading}")
            }
        }
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
    ): Company {
        return Company(
            name = remoteCurrency.company.name,
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