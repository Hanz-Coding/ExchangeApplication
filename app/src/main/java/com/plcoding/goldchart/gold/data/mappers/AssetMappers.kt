package com.plcoding.goldchart.gold.data.mappers

import com.plcoding.goldchart.core.data.database.currency.CurrencyCompanyEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyExchangeEntity
import com.plcoding.goldchart.gold.domain.model.local.Currency
import com.plcoding.goldchart.gold.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.gold.domain.model.local.CurrencyCompany
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

//----Domain To Entity
fun CurrencyExchange.toEntity(): CurrencyExchangeEntity {
    return CurrencyExchangeEntity(
        currencyCodeName = currencyCode + companyName,
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

fun CurrencyCompany.toEntity(): CurrencyCompanyEntity {
    return CurrencyCompanyEntity(
        companyName = name,
        updatedTime = updatedTime
    )
}


//---- Entity to Domain
fun CurrencyCompanyEntity.toDomain(): CurrencyCompany {
    return CurrencyCompany(
        name = companyName,
        updatedTime = updatedTime
    )
}

fun CurrencyExchangeEntity.toDomain(): CurrencyExchange {
    return CurrencyExchange(
        currencyCode = currencyCode,
        currencyName = currencyName,
        companyName = companyName,
        currencyType = currencyType,
        iconUrl = iconUrl,
        buy = buy,
        sell = sell,
        transfer = transfer,
        previousBuy = previousBuy,
        previousSell = previousSell,
        previousTransfer = previousTransfer
    )
}

fun CurrencyEntity.toDomain(): Currency {
    return Currency(
        company = company.toDomain(),
        exchangeList = exchangeList.map { it.toDomain() }
    )
}

//----------------------FORMAT

fun getTime(time: String, format: String): Long {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    val date = dateFormat.parse(time)
    return date?.time ?: 0L
}

fun convertDateStringToDouble(dateString: String): ZonedDateTime? {
    val regex = "/Date\\((-?\\d+)\\)/".toRegex()
    val matchResult = regex.find(dateString)

    return matchResult?.groups?.get(1)?.value?.toLongOrNull()?.let {
        val instant = Instant.ofEpochMilli(it)
        ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
}