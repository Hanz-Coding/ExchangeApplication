package com.plcoding.goldchart.gold.presentation.model

data class CurrencyUI(
    val company: CurrencyCompanyUI,
    val exchangeList: List<CurrencyExchangeUI>,
)
