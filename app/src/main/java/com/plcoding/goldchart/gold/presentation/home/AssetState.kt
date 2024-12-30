package com.plcoding.goldchart.gold.presentation.home

import com.plcoding.goldchart.gold.presentation.home.model.CurrencyUI

data class AssetState(
    val isLoading: Boolean = false,
    val currencyMap: Map<String, CurrencyUI> = emptyMap(),
)
