package com.plcoding.goldchart.exchange.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.goldchart.domain.Repository
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.presentation.state.ExchangeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExchangeViewModel(private val repository: Repository) : ViewModel() {
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
        println("hanz start loadLocalCurrency")
        viewModelScope.launch {
            repository.getCurrencyByCompany(companyName).collect { currency ->
                _state.update {
                    val updatedCurrency = it.currencyMap.toMutableMap()
                    updatedCurrency[companyName] = currency
                    it.copy(currencyMap = updatedCurrency)
                }
            }
        }
    }

    private fun fetchAndSaveCurrencyDB(companyName: String) {
        viewModelScope.launch {
            repository.fetchAndSaveCurrency(companyName)
        }
    }
}