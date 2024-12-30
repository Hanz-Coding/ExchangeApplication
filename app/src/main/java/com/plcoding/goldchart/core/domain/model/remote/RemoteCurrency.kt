package com.plcoding.goldchart.core.domain.model.remote

import com.plcoding.goldchart.core.domain.model.CurrencyCompany

data class RemoteCurrency(
    val company: CurrencyCompany,
    val exchangeList: List<RemoteCurrencyExchange>,
)
