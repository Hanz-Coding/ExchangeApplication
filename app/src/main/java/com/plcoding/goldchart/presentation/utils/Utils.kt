package com.plcoding.goldchart.presentation.utils

import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class DisplayNumber(
    val value: Double,
    val formatedValue: String,
)

fun Double.toDisplayNumber(): DisplayNumber {
    val decimalFormat = DecimalFormat("#,###")
    val formatedValue = decimalFormat.format(this)
    return DisplayNumber(this, formatedValue)
}

fun formatLongToDate(longTime: Long): String {
    val instant = Instant.ofEpochMilli(longTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
    return localDateTime.format(formatter)
}

