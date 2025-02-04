package com.plcoding.goldchart.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.goldchart.core.domain.utils.onSuccess
import com.plcoding.goldchart.data.mappers.generateCurrencyCode
import com.plcoding.goldchart.domain.Repository
import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.gold.domain.AssetType
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.domain.SJCCode
import com.plcoding.goldchart.home.domain.News
import com.plcoding.goldchart.home.domain.NewsRepository
import com.plcoding.goldchart.home.presentation.Constant.Companion
import com.plcoding.goldchart.home.presentation.state.HomeState
import com.plcoding.goldchart.home.presentation.mappers.toUI
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.set

class HomeViewModel(
    private val goldRepository: Repository,
    private val exchangeRepository: Repository,
    private val newRepository: NewsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.onStart {
        getLocalGold()
        fetchAndSaveGold()
        getLocalNews()
        fetchAndSaveNews()
        getLocalExchangeVCB()
        fetchAndSaveExchangeVCB()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState()
    )

    private fun fetchAndSaveExchangeVCB() {
        viewModelScope.launch {
            exchangeRepository.fetchAndSaveCurrency(CompanyName.VCB)
        }
    }

    private fun getLocalNews() {
        viewModelScope.launch {
            newRepository.getNews().collect {
                updateNews(it)
            }
        }
    }

    private fun fetchAndSaveNews() {
        viewModelScope.launch {
            newRepository.fetchAndSaveNews()
        }
    }

    private fun fetchAndSaveGold() {
        viewModelScope.launch {
            val currency = fetchSJCAndCombine()
            currency?.let {
                goldRepository.saveCurrencyToDB(it)
            }
        }
    }

    private suspend fun fetchSJCAndCombine(): Currency? {
        // Khởi tạo coroutine scope
        return coroutineScope {
            // Thực hiện fetch đồng thời cả hai API
            val apiResult =
                async {
                    goldRepository.fetchCurrency(CompanyName.SJC)
                }
            val api1Result =
                async { goldRepository.fetchCurrencyHistoryByCode(CompanyName.SJC, SJCCode.SJC) }
            val api2Result =
                async {
                    goldRepository.fetchCurrencyHistoryByCode(CompanyName.SJC, SJCCode.SJC_RING)
                }

            // Chờ kết quả từ cả hai API và gộp lại
            val result = apiResult.await()
            val result1 = api1Result.await()
            val result2 = api2Result.await()  // Chờ kết quả từ API 2

            // Gộp kết quả của cả hai API thành một đối tượng CombinedResult
            var company = Company()
            result.onSuccess {
                company = it.company
            }

            val exchangeList = mutableListOf<Exchange>()
            var sjcGold: Currency? = null
            result1.onSuccess {
                sjcGold = it
            }
            sjcGold?.let {
                exchangeList.add(generateExchangeWithHistory(SJCCode.SJC, it.exchangeList))
            }

            var ringGold: Currency? = null
            result2.onSuccess {
                ringGold = it
            }

            ringGold?.let {
                exchangeList.add(generateExchangeWithHistory(SJCCode.SJC_RING, it.exchangeList))
            }

            sjcGold?.let { sjc ->
                ringGold?.let { _ ->
                    return@coroutineScope Currency(
                        company = company, exchangeList = exchangeList
                    ).also {
                        println("hanz company $company exchangeList $exchangeList")
                    }
                }
            }
            return@coroutineScope null
        }
    }

    private fun getLocalExchangeVCB() {
        viewModelScope.launch {
            exchangeRepository.getCurrencyByCompany(CompanyName.VCB)
                .collect { currency ->
                    updateGold(currency, "VCB_USD", Companion.PRIORITY_USD_VCB)
                }
        }
    }

    private fun getLocalGold() {
        viewModelScope.launch {
            goldRepository.getCurrencyByCompany(CompanyName.SJC)
                .collect { currency ->
                    updateGold(currency, AssetType.SJC, Companion.PRIORITY_GOLD_SJC)
                    updateGold(currency, AssetType.SJC_RING, Companion.PRIORITY_GOLD_SJC_RING)
                }
        }
    }

    private fun updateGold(currency: Currency, type: String, priority: Int) {
        val goldUI = currency.toUI(type)
        goldUI?.let { gold ->
            _state.update {
                val items = it.listItem.toMutableMap()
                items[priority] = gold
                it.copy(listItem = items)
            }
        }
    }

    private fun updateNews(newsList: List<News>?) {
        newsList?.let { list ->
            val startPriority = Companion.PRIORITY_NEWS
            _state.update {
                val items = it.listItem.toMutableMap()
                list.onEachIndexed { index, news ->
                    items[index + startPriority] = news.toUI()
                }
                it.copy(listItem = items)
            }
        }
    }

    private fun generateExchangeWithHistory(code: Int, list: List<Exchange>): Exchange {
        val size = list.size
        val exchangeList = list.reversed()
        return Exchange(
            currencyCode = generateCurrencyCode(CompanyName.SJC, code.toString()),
            currencyName = exchangeList[0].currencyName,
            companyName = exchangeList[0].companyName,
            currencyType = exchangeList[0].currencyType,
            iconUrl = exchangeList[0].iconUrl,
            buy = exchangeList[0].buy,
            transfer = exchangeList[0].transfer,
            sell = exchangeList[0].sell,
            previousBuy = if (size >= 2) exchangeList[1].buy else exchangeList[0].buy,
            previousTransfer = if (size >= 2) exchangeList[1].transfer else exchangeList[0].transfer,
            previousSell = if (size >= 2) exchangeList[1].sell else exchangeList[0].sell,
        )
    }
}