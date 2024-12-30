package com.plcoding.goldchart.exchange.data.mappers

import com.plcoding.goldchart.core.data.database.currency.CurrencyCompanyEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyExchangeEntity
import com.plcoding.goldchart.core.data.networking.Constant
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVDataDto
import com.plcoding.goldchart.exchange.data.dto.vcb.VCBCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.dto.vcb.VCBDataDto
import com.plcoding.goldchart.core.domain.model.Currency
import com.plcoding.goldchart.core.domain.model.CurrencyCompany
import com.plcoding.goldchart.core.domain.model.CurrencyExchange
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrencyExchange
import com.plcoding.goldchart.exchange.domain.CompanyName
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
    val company = CurrencyCompany(CompanyName.VCB, updateTime)

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
    val company = CurrencyCompany(CompanyName.BIDV, updateTime)

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
