package com.plcoding.goldchart.exchange.presentation.model

data class CurrencyExchangeUI(
    val currencyCode: String,
    val currencyName: String,
    val companyName: String,
    val iconUrl: String,
    val buy: Double,
    val transfer: Double,
    val sell: Double,
    val previousBuy: Double,
    val previousTransfer: Double,
    val previousSell: Double,
)
