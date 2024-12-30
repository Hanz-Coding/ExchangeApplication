package com.plcoding.goldchart.exchange.presentation.state

import com.plcoding.goldchart.exchange.presentation.model.CurrencyUI

data class CurrencyState(
    val isLoading: Boolean = false,
    val currencyList: List<CurrencyUI> = emptyList(),
)