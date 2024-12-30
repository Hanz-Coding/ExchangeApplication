package com.plcoding.goldchart.gold.data.mappers

import com.plcoding.goldchart.core.domain.model.CurrencyCompany
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.core.domain.model.remote.RemoteCurrencyExchange
import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetDto
import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetResponseDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import com.plcoding.goldchart.gold.domain.CompanyName
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun convertDateStringToDouble(dateString: String): ZonedDateTime? {
    val regex = "/Date\\((-?\\d+)\\)/".toRegex()
    val matchResult = regex.find(dateString)

    return matchResult?.groups?.get(1)?.value?.toLongOrNull()?.let {
        val instant = Instant.ofEpochMilli(it)
        ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
}

//--------------------------

fun SJCAssetResponseDto.toCurrency(): RemoteCurrency {
    val updateTime = getTime(this.latestDate,"HH:mm dd/MM/yyyy")
    val company = CurrencyCompany(CompanyName.SJC, updateTime)

    val exchangeList: List<RemoteCurrencyExchange> = this.data.map { dto -> dto.toBase() }
    return RemoteCurrency(company, exchangeList)
}

fun SJCAssetDto.toBase(): RemoteCurrencyExchange {
    return RemoteCurrencyExchange(
        currencyCode = Id.toString(),
        currencyName = BranchName,
        companyName = CompanyName.SJC,
        iconUrl = "",
        buy = BuyValue,
        sell = SellValue,
        transfer = 0.0,
    )
}

fun PNJAssetResponseDto.toCurrency(): RemoteCurrency {
    val company = CurrencyCompany(CompanyName.PNJ, this.updatetime)
    val exchangeList: List<RemoteCurrencyExchange> = this.data.map { dto -> dto.toBase() }
    return RemoteCurrency(company, exchangeList)
}

fun PNJAssetDto.toBase(): RemoteCurrencyExchange {
    return RemoteCurrencyExchange(
        currencyCode = code,
        currencyName = code,
        companyName = CompanyName.SJC,
        iconUrl = "",
        buy = mua.toDouble(),
        sell = ban.toDouble(),
        transfer = 0.0,
    )
}

fun getTime(time: String, format: String): Long {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    val date = dateFormat.parse(time)
    return date?.time ?: 0L
}

fun formatLongToDate(longTime: Long): String {
    val instant = Instant.ofEpochMilli(longTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
    return localDateTime.format(formatter)
}