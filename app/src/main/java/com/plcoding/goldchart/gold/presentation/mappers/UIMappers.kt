package com.plcoding.goldchart.gold.presentation.mappers

import com.plcoding.goldchart.gold.domain.model.local.Currency
import com.plcoding.goldchart.gold.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.gold.domain.model.local.CurrencyCompany
import com.plcoding.goldchart.gold.presentation.model.CurrencyCompanyUI
import com.plcoding.goldchart.gold.presentation.model.CurrencyExchangeUI
import com.plcoding.goldchart.gold.presentation.model.CurrencyUI
import com.plcoding.goldchart.gold.presentation.model.toDisplayNumber


fun Currency.toUI(): CurrencyUI {
    return CurrencyUI(
        company = company.toUI(),
        exchangeList = exchangeList.map { it.toUI() },
    )
}

fun CurrencyCompany.toUI(): CurrencyCompanyUI {
    return CurrencyCompanyUI(
        companyName = name,
        updatedTime = updatedTime
    )
}

fun CurrencyExchange.toUI(): CurrencyExchangeUI {
    return CurrencyExchangeUI(
        currencyCode = currencyCode,
        currencyName = currencyName,
        currencyType = currencyType,
        companyName = companyName,
        iconUrl = iconUrl,
        buy = buy.toDisplayNumber(),
        sell = sell.toDisplayNumber(),
        transfer = transfer.toDisplayNumber(),
        previousBuy = previousBuy.toDisplayNumber(),
        previousSell = previousSell.toDisplayNumber(),
        previousTransfer = previousTransfer.toDisplayNumber()
    )
}
