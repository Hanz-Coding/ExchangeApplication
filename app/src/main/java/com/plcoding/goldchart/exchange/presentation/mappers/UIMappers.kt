package com.plcoding.goldchart.exchange.presentation.mappers

import com.plcoding.goldchart.core.domain.model.Currency
import com.plcoding.goldchart.core.domain.model.CurrencyCompany
import com.plcoding.goldchart.core.domain.model.CurrencyExchange
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrencyExchange
import com.plcoding.goldchart.exchange.presentation.model.CurrencyCompanyUI
import com.plcoding.goldchart.exchange.presentation.model.CurrencyExchangeUI
import com.plcoding.goldchart.exchange.presentation.model.CurrencyUI
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun CurrencyCompany.toUI(): CurrencyCompanyUI {
    return CurrencyCompanyUI(
        companyName = companyName,
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
    val newCompany = CurrencyCompany(
        companyName = company.companyName,
        updatedTime = company.updatedTime
    )
    return Currency(
        newCompany,
        exchangeList.map { it.toUI() }
    )
}