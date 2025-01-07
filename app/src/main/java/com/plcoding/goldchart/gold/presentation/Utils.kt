package com.plcoding.goldchart.gold.presentation

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatLongToDate(longTime: Long?): String {
    if (longTime == null) return ""
    val instant = Instant.ofEpochMilli(longTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
    return localDateTime.format(formatter)
}
