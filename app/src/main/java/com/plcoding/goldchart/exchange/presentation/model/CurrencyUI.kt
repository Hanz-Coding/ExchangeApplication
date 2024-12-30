package com.plcoding.goldchart.exchange.presentation.model

data class CurrencyUI(
    val company: CurrencyCompanyUI,
    val exchangeList: List<CurrencyExchangeUI>,
)