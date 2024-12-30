package com.plcoding.goldchart.exchange.presentation.state

import com.plcoding.goldchart.exchange.presentation.model.CurrencyUI

data class ExchangeState(
    val isLoading: Boolean = false,
    val currencyMap: Map<String, CurrencyUI?> = emptyMap(),
)