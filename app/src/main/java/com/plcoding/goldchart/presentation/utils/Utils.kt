package com.plcoding.goldchart.presentation.utils

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class DisplayNumber(
    val value: Double,
    val formatedValue: String,
)

fun formatLongToDate(longTime: Long): String {
    val instant = Instant.ofEpochMilli(longTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
    return localDateTime.format(formatter)
}

fun Double.toDisplayNumber(): String = DecimalFormat("#,###").format(this)

@SuppressLint("DefaultLocale")
fun Double.toDisplayPercent(): String = String.format("%.2f", this)

