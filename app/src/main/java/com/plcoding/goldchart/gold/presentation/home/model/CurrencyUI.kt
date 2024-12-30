package com.plcoding.goldchart.gold.presentation.home.model

data class CurrencyUI(
    val company: CurrencyCompanyUI,
    val exchangeList: List<CurrencyExchangeUI>,
)