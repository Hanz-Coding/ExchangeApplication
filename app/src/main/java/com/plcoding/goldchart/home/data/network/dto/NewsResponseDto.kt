package com.plcoding.goldchart.home.data.network.dto

import com.google.gson.annotations.SerializedName

class NewsResponseDto(
    @SerializedName("Data")
    val newsList: List<NewsDto>?,
)