package com.plcoding.goldchart.home.data.mappers

import com.plcoding.goldchart.core.data.networking.constructUrl
import com.plcoding.goldchart.data.database.news.NewsEntity
import com.plcoding.goldchart.home.data.Constant
import com.plcoding.goldchart.home.data.network.dto.NewsDto
import com.plcoding.goldchart.home.domain.News
import com.plcoding.goldchart.home.presentation.convertDateString

fun NewsDto.toDomain(): News {
    return News(
        url = constructUrl(url = url ?: "", baseUrl = Constant.BASE_URL_CAFEF),
        title = title ?: "",
        description = description ?: "",
        iconUrl = iconUrl ?: "",
        dateStr = dateStr?.let { convertDateString(it) } ?: ""
    )
}

fun News.toEntity(): NewsEntity {
    return NewsEntity(
        url = url,
        title = title,
        description = description,
        iconUrl = iconUrl,
        dateStr = dateStr
    )
}