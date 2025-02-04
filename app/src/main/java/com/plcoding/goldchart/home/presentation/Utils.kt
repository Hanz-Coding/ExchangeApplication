package com.plcoding.goldchart.home.presentation

import android.annotation.SuppressLint
import com.plcoding.goldchart.core.domain.utils.dateFormatFull
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Double.toDisplayNumber(): String = DecimalFormat("#,###").format(this)

@SuppressLint("DefaultLocale")
fun Double.toDisplayPercent(): String = String.format("%.2f", this)

fun formatLongToDate(longTime: Long): String {
    // Chuyển Long (miliseconds) thành LocalDateTime
    val instant = Instant.ofEpochMilli(longTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Định dạng thời gian theo kiểu HH:mm:ss dd/MM/yyyy
    val formatter = DateTimeFormatter.ofPattern(dateFormatFull)
    return localDateTime.format(formatter)
}

fun convertDateString(dateString: String): String {
    // Lấy timestamp từ chuỗi
    val timestamp = dateString.removeSurrounding("/Date(", ")/").toLong()
    // Chuyển đổi timestamp thành Date
    val date = Date(timestamp)
    // Định dạng ngày giờ theo yêu cầu
    val dateFormat = SimpleDateFormat(dateFormatFull, Locale.getDefault())
    return dateFormat.format(date)
}
