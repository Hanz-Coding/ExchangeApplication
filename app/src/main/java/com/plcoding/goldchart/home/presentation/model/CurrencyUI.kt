package com.plcoding.goldchart.home.presentation.model

data class CurrencyUI(
    val company: CurrencyCompanyUI,
    val exchangeList: List<CurrencyExchangeUI>,
)