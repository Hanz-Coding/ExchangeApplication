package com.plcoding.goldchart.core.data.mappers

import com.plcoding.goldchart.core.data.database.currency.CurrencyCompanyEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyExchangeEntity
import com.plcoding.goldchart.core.domain.model.Currency
import com.plcoding.goldchart.core.domain.model.CurrencyCompany
import com.plcoding.goldchart.core.domain.model.CurrencyExchange

fun CurrencyExchange.toEntity(): CurrencyExchangeEntity {
    return CurrencyExchangeEntity(
        currencyCodeName = currencyCode + companyName,
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

fun CurrencyExchangeEntity.toDomain(): CurrencyExchange {
    return CurrencyExchange(
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

fun CurrencyCompany.toEntity(): CurrencyCompanyEntity {
    return CurrencyCompanyEntity(
        companyName = companyName,
        updatedTime = updatedTime
    )
}

fun CurrencyCompanyEntity.toDomain(): CurrencyCompany {
    return CurrencyCompany(
        companyName = companyName,
        updatedTime = updatedTime
    )
}

fun CurrencyEntity.toDomain(): Currency {
    return Currency(
        company = company.toDomain(),
        exchangeList = exchangeList.map { it.toDomain() }
    )
}
