package com.plcoding.goldchart.core.domain.model

data class Currency(
    val company: CurrencyCompany,
    val exchangeList: List<CurrencyExchange>,
)
