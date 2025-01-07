package com.plcoding.goldchart.exchange.presentation.mappers

import com.plcoding.goldchart.exchange.domain.model.local.Currency
import com.plcoding.goldchart.exchange.domain.model.local.Company
import com.plcoding.goldchart.exchange.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrencyExchange
import com.plcoding.goldchart.exchange.presentation.model.CurrencyCompanyUI
import com.plcoding.goldchart.exchange.presentation.model.CurrencyExchangeUI
import com.plcoding.goldchart.exchange.presentation.model.CurrencyUI

fun Company.toUI(): CurrencyCompanyUI {
    return CurrencyCompanyUI(
        companyName = name,
        updatedTime = updatedTime
    )
}

fun CurrencyExchange.toUI(): CurrencyExchangeUI {
    return CurrencyExchangeUI(
        currencyCode = currencyCode,
        currencyName = currencyName,
        companyName = companyName,
        iconUrl = iconUrl,
        buy = buy,
        sell = sell,
        transfer = transfer,
        previousBuy = previousBuy,
        previousSell = previousSell,
        previousTransfer = previousTransfer
    )
}

fun Currency.toUI(): CurrencyUI {
    return CurrencyUI(
        company = company.toUI(),
        exchangeList = exchangeList.map { it.toUI() },
    )
}


fun RemoteCurrencyExchange.toUI(): CurrencyExchange {
    return CurrencyExchange(
        currencyCode = currencyCode,
        currencyName = currencyName,
        currencyType = currencyType,
        companyName = companyName,
        iconUrl = iconUrl,
        buy = buy,
        transfer = transfer,
        sell = sell,
        previousBuy = buy,
        previousTransfer = transfer,
        previousSell = sell,
    )
}

fun RemoteCurrency.toDomain(): Currency {
    val newCompany = Company(
        name = company.name,
        updatedTime = company.updatedTime
    )
    return Currency(
        newCompany,
        exchangeList.map { it.toUI() }
    )
}