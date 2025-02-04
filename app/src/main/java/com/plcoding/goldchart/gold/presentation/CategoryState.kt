package com.plcoding.goldchart.gold.presentation

import com.plcoding.goldchart.gold.presentation.model.CompanyUI

data class CategoryState(
    val categoryAssetList: List<Any?> = emptyList(),
    val currencyCompany: CompanyUI? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
