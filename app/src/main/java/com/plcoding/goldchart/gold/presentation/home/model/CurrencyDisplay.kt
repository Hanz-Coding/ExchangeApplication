package com.plcoding.goldchart.gold.presentation.home.model

import androidx.compose.ui.graphics.Color

data class CurrencyDisplay(
    val title: String,
    val updateTime: String,
    val buy: String,
    val buyChange: String,
    val buyChangePercent: String,
    val buyColor: Color,
    val sell: String,
    val sellChange: String,
    val sellChangePercent: String,
    val sellColor: Color,
    val backgroundColor: Color,
)
