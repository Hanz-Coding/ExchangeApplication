package com.plcoding.goldchart.exchange.data.mappers

import com.plcoding.goldchart.core.data.database.currency.CurrencyCompanyEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyExchangeEntity
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVDataDto
import com.plcoding.goldchart.exchange.data.dto.vcb.VCBCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.dto.vcb.VCBDataDto
import com.plcoding.goldchart.exchange.domain.model.local.Company
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrencyExchange
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.data.Constant
import com.plcoding.goldchart.exchange.domain.model.local.Currency
import com.plcoding.goldchart.exchange.domain.model.local.CurrencyExchange
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun VCBDataDto.toBase(): RemoteCurrencyExchange {
    return RemoteCurrencyExchange(
        currencyCode = currencyCode,
        currencyName = currencyName,
        companyName = CompanyName.VCB,
        iconUrl = updateIconUrl(CompanyName.VCB, icon),
        buy = cash.toDouble(),
        sell = sell.toDouble(),
        transfer = transfer.toDouble(),
    )
}

fun updateIconUrl(companyName: String, iconUrl: String): String {
    return when (companyName) {
        CompanyName.VCB -> Constant.BASE_URL_VCB + iconUrl
        CompanyName.BIDV -> Constant.BASE_URL_BIDV + iconUrl
        else -> iconUrl
    }
}

fun VCBCurrencyResponseDto.toCurrency(): RemoteCurrency {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val updatedDate = dateFormat.parse(this.UpdatedDate)
    val updateTime = updatedDate?.time ?: 0L
    val company = Company(CompanyName.VCB, updateTime)

    return RemoteCurrency(company,
        this.Data.map { dto ->
            dto.toBase()
        })
}

fun BIDVCurrencyResponseDto.toCurrency(): RemoteCurrency {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val remoteDate = this.day_vi + " " + this.hour
    val updatedDate = dateFormat.parse(remoteDate)
    val updateTime = updatedDate?.time ?: 0L
    val company = Company(CompanyName.BIDV, updateTime)

    return RemoteCurrency(company,
        this.data.map { dto ->
            dto.toBase()
        })
}

fun BIDVDataDto.toBase(): RemoteCurrencyExchange {
    return RemoteCurrencyExchange(
        currencyCode = currency,
        currencyName = nameVI,
        companyName = CompanyName.BIDV,
        iconUrl = updateIconUrl(CompanyName.BIDV, image),
        buy = convertStringToDouble(muaTm),
        sell = convertStringToDouble(ban),
        transfer = convertStringToDouble(muaCk),
    )
}

fun convertStringToDouble(input: String): Double {
    return if (input.contains("-")) {
        0.0
    } else {
        input.replace(",", "").toDouble()
    }
}

fun formatLongToDate(longTime: Long): String {
    // Chuyển Long (miliseconds) thành LocalDateTime
    val instant = Instant.ofEpochMilli(longTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Định dạng thời gian theo kiểu HH:mm:ss dd/MM/yyyy
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
    return localDateTime.format(formatter)
}

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

fun Company.toEntity(): CurrencyCompanyEntity {
    return CurrencyCompanyEntity(
        companyName = name,
        updatedTime = updatedTime
    )
}

fun CurrencyCompanyEntity.toDomain(): Company {
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
