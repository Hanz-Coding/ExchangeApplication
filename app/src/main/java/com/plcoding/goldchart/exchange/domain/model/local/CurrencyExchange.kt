package com.plcoding.goldchart.exchange.domain.model.local

data class CurrencyExchange(
    val currencyCode: String,
    val currencyName: String,
    val currencyType: String,
    val companyName: String,
    val iconUrl: String,
    val buy: Double,
    val transfer: Double,
    val sell: Double,
    val previousBuy: Double,
    val previousTransfer: Double,
    val previousSell: Double,
)
