package com.plcoding.goldchart.core.domain.model.remote

data class RemoteCurrencyExchange(
    val currencyCode: String,
    val currencyName: String,
    val companyName: String,
    val iconUrl: String,
    val buy: Double,
    val transfer: Double,
    val sell: Double,
)
