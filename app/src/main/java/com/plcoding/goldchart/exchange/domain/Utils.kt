package com.plcoding.goldchart.exchange.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

object CompanyName {
    const val VCB = "VCB"
    const val BIDV = "BIDV"
}

fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val agoDay = currentDate.minusDays(Random.nextInt(1, 7).toLong())// Lấy ngày hiện tại
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    println("log getCurrentDate $agoDay")
    return agoDay.format(formatter)  // Chuyển đổi thành chuỗi theo định dạng
}