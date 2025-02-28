package com.plcoding.goldchart.gold.presentation.mappers

import androidx.compose.ui.graphics.Color
import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.gold.presentation.model.CompanyUI
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
        buy = this.buy.toDisplayNumber(),
        buyChange = buyChange.toDisplayNumber(),
        buyChangePercent = "${buyChangePercent.toDisplayNumber()}%",
        buyColor = buyColor,
        sell = this.sell.toDisplayNumber(),
        sellChange = sellChange.toDisplayNumber(),
        sellChangePercent = "${sellChangePercent.toDisplayNumber()}%",
        sellColor = sellColor,
        backgroundColor = backgroundColor
    )
}