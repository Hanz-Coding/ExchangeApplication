package com.plcoding.goldchart.home.presentation.mappers

import androidx.compose.runtime.currentComposer
import androidx.compose.ui.graphics.Color
import com.plcoding.goldchart.exchange.domain.model.local.Currency
import com.plcoding.goldchart.exchange.domain.model.local.Company
import com.plcoding.goldchart.exchange.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrency
import com.plcoding.goldchart.exchange.domain.model.remote.RemoteCurrencyExchange
import com.plcoding.goldchart.gold.domain.getTitleForSJC
import com.plcoding.goldchart.gold.presentation.model.CurrencyUI
import com.plcoding.goldchart.home.presentation.formatLongToDate
import com.plcoding.goldchart.home.presentation.model.CurrencyCompanyUI
import com.plcoding.goldchart.home.presentation.model.CurrencyDisplay
import com.plcoding.goldchart.home.presentation.model.CurrencyExchangeUI
import com.plcoding.goldchart.home.presentation.toDisplayNumber
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

val colorText: (Double) -> Color = {
    if (it >= 0) Color(0xff08A045) else Color.Red
}

val colorBackground: (Double) -> Color = {
    if (it >= 0) Color(0xffc7f9cc) else Color(0xffFFE2E1)
}

val percent:(Double, Double)->Double = { change,previous->
    if (previous == 0.0) 0.0 else {
        change * 100 / previous
    }
}

fun CurrencyUI.toDisplay(type: String): CurrencyDisplay? {
    val updatedTime = company.updatedTime
    val exchangeUI = exchangeList.find {
        it.currencyCode == type
    }
    if (exchangeUI == null) return null
    val title = getTitleForSJC(type)

    val buyChange = exchangeUI.buy.value - exchangeUI.previousBuy.value
    val sellChange = exchangeUI.sell.value - exchangeUI.previousSell.value

    val buyChangePercent = percent(buyChange, exchangeUI.previousBuy.value)
    val sellChangePercent = percent(sellChange, exchangeUI.previousSell.value)

    val buyColor = colorText(buyChange)
    val sellColor = colorText(sellChange)
    val backgroundColor = colorBackground(buyChange)

    return CurrencyDisplay(
        title = title,
        updateTime = formatLongToDate(updatedTime),
        buy = exchangeUI.buy.formatedValue,
        buyChange = buyChange.toDisplayNumber().formatedValue,
        buyChangePercent = "${buyChangePercent.toDisplayNumber().formatedValue}%",
        buyColor = buyColor,
        sell = exchangeUI.sell.formatedValue,
        sellChange = sellChange.toDisplayNumber().formatedValue,
        sellChangePercent = "${sellChangePercent.toDisplayNumber().formatedValue}%",
        sellColor = sellColor,
        backgroundColor = backgroundColor
    )
}

//fun CurrencyExchange.toUI(): CurrencyExchangeUI {
//    return CurrencyExchangeUI(
//        currencyCode = currencyCode,
//        currencyName = currencyName,
//        companyName = companyName,
//        iconUrl = iconUrl,
//        buy = buy,
//        sell = sell,
//        transfer = transfer,
//        previousBuy = previousBuy,
//        previousSell = previousSell,
//        previousTransfer = previousTransfer
//    )
//}

//fun Currency.toUI(): CurrencyUI {
//    return CurrencyUI(
//        company = company.toUI(),
//        exchangeList = exchangeList.map { it.toUI() },
//    )
//}

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
