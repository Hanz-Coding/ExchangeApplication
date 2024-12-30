package com.plcoding.goldchart.gold.presentation.mappers

import androidx.compose.ui.graphics.Color
import com.plcoding.goldchart.exchange.domain.model.local.Currency
import com.plcoding.goldchart.exchange.domain.model.local.Company
import com.plcoding.goldchart.exchange.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrencyExchange
import com.plcoding.goldchart.gold.domain.getTitleForSJC
import com.plcoding.goldchart.gold.presentation.home.model.CurrencyCompanyUI
import com.plcoding.goldchart.gold.presentation.home.model.CurrencyDisplay
import com.plcoding.goldchart.gold.presentation.home.model.CurrencyExchangeUI
import com.plcoding.goldchart.gold.presentation.home.model.CurrencyUI
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Company.toUI(): CurrencyCompanyUI {
    return CurrencyCompanyUI(
        companyName = name,
        updatedTime = updatedTime
    )
}

fun CurrencyUI.toDisplay(type: Int): CurrencyDisplay? {
    val updatedTime = this.company.updatedTime
    val exchangeUI = this.exchangeList.find {
        it.currencyCode.toInt() == type
    }
    if (exchangeUI == null) return null
    val title = getTitleForSJC(type)
    val buy = DecimalFormat("#,###").format(exchangeUI.buy.toLong())
    val buyChange = exchangeUI.buy - exchangeUI.previousBuy
    val buyChangePercent = if (buyChange == 0.0 || exchangeUI.previousBuy == 0.0) 0.0 else {
        buyChange * 100 / exchangeUI.previousBuy
    }
    val buyColor = if (buyChange >= 0) Color.Green else Color.Red
    val backgroundColor = if (buyChange >= 0) Color(0xffc7f9cc) else Color(0xffFFE2E1)

    val sell = DecimalFormat("#,###").format(exchangeUI.sell.toLong())
    val sellChange = exchangeUI.sell - exchangeUI.previousSell
    val sellChangePercent = if (sellChange == 0.0 || exchangeUI.previousSell == 0.0) 0.0 else {
        sellChange * 100 / exchangeUI.previousSell
    }
    val sellColor = if (sellChange >= 0) Color.Green else Color.Red

    return CurrencyDisplay(
        title = title,
        updateTime = formatLongToDate(updatedTime),
        buy = buy,
        buyChange = buyChange.toString()+"đ",
        buyChangePercent = "$buyChangePercent%",
        buyColor = buyColor,
        sell = sell,
        sellChange = sellChange.toString()+"đ",
        sellChangePercent = "$sellChangePercent%",
        sellColor = sellColor,
        backgroundColor = backgroundColor
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
    val newCompany = Company(
        name = company.name,
        updatedTime = company.updatedTime
    )
    return Currency(
        newCompany,
        exchangeList.map { it.toUI() }
    )
}

fun formatLongToDate(longTime: Long): String {
    // Chuyển Long (miliseconds) thành LocalDateTime
    val instant = Instant.ofEpochMilli(longTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Định dạng thời gian theo kiểu HH:mm:ss dd/MM/yyyy
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
    return localDateTime.format(formatter)
}
