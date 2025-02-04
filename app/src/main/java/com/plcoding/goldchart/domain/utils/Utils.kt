package com.plcoding.goldchart.domain.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.getStringFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.format(formatter)
}
