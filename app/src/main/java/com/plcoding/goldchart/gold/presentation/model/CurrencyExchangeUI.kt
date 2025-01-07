package com.plcoding.goldchart.gold.presentation.model

import java.text.DecimalFormat

data class CurrencyExchangeUI(
    val currencyCode: String,
    val currencyName: String,
    val companyName: String,
    val currencyType: String,
    val iconUrl: String,
    val buy: DisplayNumber,
    val transfer: DisplayNumber,
    val sell: DisplayNumber,
    val previousBuy: DisplayNumber,
    val previousTransfer: DisplayNumber,
    val previousSell: DisplayNumber,
)

data class DisplayNumber(
    val value: Double,
    val formatedValue: String,
)

fun Double.toDisplayNumber(): DisplayNumber {
    val decimalFormat = DecimalFormat("#,###")
    val formatedValue = decimalFormat.format(this)
    return DisplayNumber(this, formatedValue)
}
