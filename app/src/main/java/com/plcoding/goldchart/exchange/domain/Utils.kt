package com.plcoding.goldchart.exchange.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CompanyName {
    const val VCB = "VCB"
    const val BIDV = "BIDV"
}

fun getCurrentDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return date.format(formatter)  // Chuyển đổi thành chuỗi theo định dạng
}
