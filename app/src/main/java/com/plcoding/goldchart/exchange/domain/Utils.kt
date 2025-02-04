package com.plcoding.goldchart.exchange.domain

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.random.Random

object CompanyName {
    const val VCB = "VCB"
    const val BIDV = "BIDV"
}

fun getCurrentDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return date.format(formatter)  // Chuyển đổi thành chuỗi theo định dạng
}

fun formatLongToDate(longTime: Long): String {
    // Chuyển Long (miliseconds) thành LocalDateTime
    val instant = Instant.ofEpochMilli(longTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Định dạng thời gian theo kiểu HH:mm:ss dd/MM/yyyy
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")
    return localDateTime.format(formatter)
}
