package com.plcoding.goldchart.data.mappers

import com.plcoding.goldchart.data.database.currency.CompanyEntity
import com.plcoding.goldchart.data.database.currency.CurrencyEntity
import com.plcoding.goldchart.data.database.currency.ExchangeEntity
import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange

fun Exchange.toEntity(): ExchangeEntity {
    return ExchangeEntity(
        currencyId = generateCurrencyId(companyName, currencyCode),
        currencyCode = currencyCode,
        currencyName = currencyName,
        currencyType = currencyType,
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

fun generateCurrencyId(company: String, code: String): String {
    return company + "_" + code
}

fun ExchangeEntity.toDomain(): Exchange {
    return Exchange(
        currencyId = currencyId,
        currencyCode = currencyCode,
        currencyName = currencyName,
        currencyType = currencyType,
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

fun Company.toEntity(): CompanyEntity {
    return CompanyEntity(
        companyName = name,
        updatedTime = updatedTime
    )
}

fun CompanyEntity.toDomain(): Company {
    return Company(
        name = companyName,
        updatedTime = updatedTime
    )
}

fun CurrencyEntity.toDomain(): Currency {
    return Currency(
        company = company.toDomain(),
        exchangeList = exchangeList.map { it.toDomain() }
    )
}