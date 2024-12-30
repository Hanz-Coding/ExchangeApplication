package com.plcoding.goldchart.gold.presentation.gold_price

data class CategoryState(
    val categoryAssetList: List<Any?> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
