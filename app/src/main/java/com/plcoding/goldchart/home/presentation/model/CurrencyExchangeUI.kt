package com.plcoding.goldchart.home.presentation.model

import com.plcoding.goldchart.home.presentation.DisplayNumber

data class CurrencyExchangeUI(
    val currencyCode: String,
    val currencyName: String,
    val companyName: String,
    val iconUrl: String,
    val buy: DisplayNumber,
    val transfer: DisplayNumber,
    val sell: DisplayNumber,
    val previousBuy: DisplayNumber,
    val previousTransfer: DisplayNumber,
    val previousSell: DisplayNumber,
)
