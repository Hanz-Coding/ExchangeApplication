package com.plcoding.goldchart.gold.presentation.mappers

import androidx.compose.ui.graphics.Color
import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.gold.presentation.model.CompanyUI
import com.plcoding.goldchart.gold.presentation.model.CurrencyUI
import com.plcoding.goldchart.gold.presentation.model.ExchangeUI
import com.plcoding.goldchart.presentation.utils.toDisplayNumber

val colorText: (Double) -> Color = {
    if (it >= 0) Color(0xff08A045) else Color.Red
}

val colorBackground: (Double) -> Color = {
    if (it >= 0) Color(0xffc7f9cc) else Color(0xffFFE2E1)
}

val percent: (Double, Double) -> Double = { change, previous ->
    if (previous == 0.0) 0.0 else {
        change * 100 / previous
    }
}

fun Company.toUI(): CompanyUI {
    return CompanyUI(
        companyName = this.name,
        updatedTime = this.updatedTime
    )
}

fun Currency.toUI(): CurrencyUI {
    return CurrencyUI(
        company = this.company.toUI(),
        exchangeList = this.exchangeList.map { it.toDisplay() }
    )
}

fun Exchange.toDisplay(): ExchangeUI {
    val buyChange = this.buy - this.previousBuy
    val sellChange = this.sell - this.previousSell

    val buyChangePercent = percent(buyChange, this.previousBuy)
    val sellChangePercent = percent(sellChange, this.previousSell)

    val buyColor = colorText(buyChange)
    val sellColor = colorText(sellChange)
    val backgroundColor = colorBackground(buyChange)
    return ExchangeUI(
        currencyName = this.currencyName,
        buy = this.buy.toDisplayNumber().formatedValue,
        buyChange = buyChange.toDisplayNumber().formatedValue,
        buyChangePercent = "${buyChangePercent.toDisplayNumber().formatedValue}%",
        buyColor = buyColor,
        sell = this.sell.toDisplayNumber().formatedValue,
        sellChange = sellChange.toDisplayNumber().formatedValue,
        sellChangePercent = "${sellChangePercent.toDisplayNumber().formatedValue}%",
        sellColor = sellColor,
        backgroundColor = backgroundColor
    )
}