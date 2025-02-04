package com.plcoding.goldchart.gold.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.goldchart.domain.Repository
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.presentation.mappers.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GoldPriceViewModel(
    private val repository: Repository,
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
            repository.getCurrencyByCompany(companyName).collect { currency ->
                val exchangeList = currency.exchangeList.groupBy { it.currencyType }
                val result: MutableList<Any> = mutableListOf()
                exchangeList.forEach {
                    result.add(it.key)
                    result.addAll(it.value)
                }
                val currencyCompany = currency.company
                state.update {
                    it.copy(
                        categoryAssetList = result,
                        currencyCompany = currencyCompany.toUI(),
                        isLoading = result.isEmpty()
                    )
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