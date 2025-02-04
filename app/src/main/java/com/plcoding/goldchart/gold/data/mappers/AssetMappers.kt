package com.plcoding.goldchart.gold.data.mappers

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale

fun getTime(time: String, format: String): Long {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    val date = dateFormat.parse(time)
    return date?.time ?: 0L
}

fun convertDateStringToDouble(dateString: String): ZonedDateTime? {
    val regex = "/Date\\((-?\\d+)\\)/".toRegex()
    val matchResult = regex.find(dateString)

    return matchResult?.groups?.get(1)?.value?.toLongOrNull()?.let {
        val instant = Instant.ofEpochMilli(it)
        ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
}