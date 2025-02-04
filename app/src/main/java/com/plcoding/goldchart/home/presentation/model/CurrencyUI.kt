package com.plcoding.goldchart.home.presentation.model

import androidx.compose.ui.graphics.Color

data class CurrencyUI(
    val title: String,
    val updateTime: String,
    val backgroundColor: Color,
    val priceList: List<PriceUI>,
)
