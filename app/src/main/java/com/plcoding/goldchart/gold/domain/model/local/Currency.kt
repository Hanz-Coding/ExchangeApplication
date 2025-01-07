package com.plcoding.goldchart.gold.domain.model.local

data class Currency(
    val company: CurrencyCompany,
    val exchangeList: List<CurrencyExchange>,
)
