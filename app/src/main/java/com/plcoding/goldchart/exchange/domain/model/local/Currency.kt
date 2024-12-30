package com.plcoding.goldchart.exchange.domain.model.local

data class Currency(
    val company: Company,
    val exchangeList: List<CurrencyExchange>,
)
