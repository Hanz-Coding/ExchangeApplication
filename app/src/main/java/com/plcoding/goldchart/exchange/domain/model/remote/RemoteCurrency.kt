package com.plcoding.goldchart.exchange.domain.model.remote

import com.plcoding.goldchart.exchange.domain.model.local.Company

data class RemoteCurrency(
    val company: Company,
    val exchangeList: List<RemoteCurrencyExchange>,
)
