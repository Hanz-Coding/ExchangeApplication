package com.plcoding.goldchart.home.presentation

import com.plcoding.goldchart.gold.presentation.model.CurrencyUI

data class GoldState(
    val isLoading: Boolean = false,
    val currencyMap: Map<String, CurrencyUI> = emptyMap(),
)
