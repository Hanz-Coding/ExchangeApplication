package com.plcoding.goldchart.gold.presentation

import com.plcoding.goldchart.gold.presentation.model.CurrencyCompanyUI

data class CategoryState(
    val categoryAssetList: List<Any?> = emptyList(),
    val currencyCompany: CurrencyCompanyUI? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
