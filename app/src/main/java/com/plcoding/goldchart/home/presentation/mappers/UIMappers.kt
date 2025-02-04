package com.plcoding.goldchart.home.presentation.mappers

import androidx.compose.ui.graphics.Color
import com.plcoding.goldchart.R
import com.plcoding.goldchart.data.database.news.NewsEntity
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.gold.domain.getTitleCurrency
import com.plcoding.goldchart.home.domain.News
import com.plcoding.goldchart.home.presentation.formatLongToDate
import com.plcoding.goldchart.home.presentation.model.CurrencyUI
import com.plcoding.goldchart.home.presentation.model.NewsUI
import com.plcoding.goldchart.home.presentation.model.PriceUI
import com.plcoding.goldchart.presentation.utils.toDisplayNumber
import com.plcoding.goldchart.presentation.utils.toDisplayPercent

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

fun Currency.toUI(code: String): CurrencyUI? {
    val updatedTime = company.updatedTime
    val exchangeUI = exchangeList.find {
        it.currencyId == code
    }
    if (exchangeUI == null) return null
    val title = getTitleCurrency(code)

    val buyChange = exchangeUI.buy - exchangeUI.previousBuy
    val backgroundColor = colorBackground(buyChange)

    val priceList = mutableListOf<PriceUI>()
    val buyPrice =
        getPriceUI(
            R.string.buy_title,
            R.string.buy_sjc_des,
            exchangeUI.buy,
            exchangeUI.previousBuy
        )
    priceList.add(buyPrice)

    if (exchangeUI.transfer != 0.0) {
        val transfer = getPriceUI(
            R.string.transfer_usd_vcb_title,
            R.string.transfer_usd_vcs_des,
            exchangeUI.transfer,
            exchangeUI.previousTransfer
        )
        priceList.add(transfer)
    }

    val sellPrice = getPriceUI(
        R.string.sell_title,
        R.string.sell_sjc_des,
        exchangeUI.sell,
        exchangeUI.previousSell
    )
    priceList.add(sellPrice)

    return CurrencyUI(
        title = title,
        updateTime = formatLongToDate(updatedTime),
        backgroundColor = backgroundColor,
        priceList = priceList
    )
}

private fun getPriceUI(title: Int, des: Int, value: Double, prevValue: Double): PriceUI {
    val change = value - prevValue
    val changePercent = percent(change, prevValue)
    val color = colorText(change)

    return PriceUI(
        title = title,
        des = des,
        value = value.toDisplayNumber(),
        valueChange = change.toDisplayNumber(),
        valueChangePercent = "${changePercent.toDisplayPercent()}%",
        color = color
    )
}

fun News.toUI(): NewsUI {
    return NewsUI(
        url = url, title = title, description = description, iconUrl = iconUrl, dateStr = dateStr
    )
}

fun NewsEntity.toDomain(): News {
    return News(
        url = url, title = title, description = description, iconUrl = iconUrl, dateStr = dateStr
    )
}