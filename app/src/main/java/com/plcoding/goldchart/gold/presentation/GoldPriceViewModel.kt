package com.plcoding.goldchart.gold.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.goldchart.core.domain.utils.onError
import com.plcoding.goldchart.core.domain.utils.onSuccess
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.domain.model.local.Currency
import com.plcoding.goldchart.gold.domain.repository.AssetsRepository
import com.plcoding.goldchart.gold.presentation.mappers.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class GoldPriceViewModel(
    private val repository: AssetsRepository,
) : ViewModel() {

    private val _stateSJC = MutableStateFlow(CategoryState())
    val stateSJC = _stateSJC.onStart {
        loadLocalAssets(CompanyName.SJC)
        fetchAndSaveCurrencyDB(CompanyName.SJC)
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

    private fun getTabState(companyName: String) = when (companyName) {
        CompanyName.SJC -> _stateSJC
        CompanyName.PNJ -> _statePNJ
        else -> throw IllegalArgumentException("Invalid company name")
    }

    private fun loadLocalAssets(companyName: String) {
        val state = getTabState(companyName)
        viewModelScope.launch {
            state.update {
                it.copy(isLoading = true)
            }
            repository.getCurrencyByCompanyName(companyName).collect { currency ->
                val currencyUI = currency.toUI()
                val exchangeList = currencyUI.exchangeList.groupBy { it.currencyType }
                val result: MutableList<Any> = mutableListOf()
                exchangeList.forEach {
                    result.add(it.key)
                    result.addAll(it.value)
                }
                val currencyCompany = currencyUI.company
                println("hanz result ${result}")
                state.update {
                    it.copy(
                        categoryAssetList = result,
                        currencyCompany = currencyCompany,
                        isLoading = result.isEmpty()
                    )
                }
            }
        }
    }

    private fun fetchAndSaveCurrencyDB(companyName: String) {
        viewModelScope.launch {
            repository.fetchCurrencyByName(companyName, LocalDate.now())
                .onSuccess { remoteCurrency ->
                    val filterCurrency = remoteCurrency.copy(
                        company = remoteCurrency.company,
                        exchangeList = remoteCurrency.exchangeList.filter { it.sell != 0.0 && it.buy != 0.0 }
                    )
                    val localCurrency = getLocalCurrency(companyName)
                    println("hanz fetchAndSaveCurrencyDB onSuccess ${filterCurrency.exchangeList}")
                    val needUpdateDB = needUpdateDB(localCurrency, filterCurrency)
                    if (needUpdateDB) {
                        saveDB(filterCurrency)
                    }
                }.onError {
                println("hanz fetchAndSaveCurrencyDB onError")
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

    private fun needUpdateDB(localCurrency: Currency?, remoteCurrency: Currency): Boolean {
        return localCurrency == null ||
                remoteCurrency.company.updatedTime - localCurrency.company.updatedTime != 0L
    }

}