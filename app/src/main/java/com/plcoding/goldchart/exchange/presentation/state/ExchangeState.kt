package com.plcoding.goldchart.exchange.presentation.state

import com.plcoding.goldchart.domain.model.Currency

data class ExchangeState(
    val isLoading: Boolean = false,
    val currencyMap: Map<String, Currency?> = emptyMap(),
)