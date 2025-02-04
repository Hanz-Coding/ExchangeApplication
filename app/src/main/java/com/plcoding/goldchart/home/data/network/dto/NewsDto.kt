package com.plcoding.goldchart.home.data.network.dto

import com.google.gson.annotations.SerializedName

class NewsDto(
    @SerializedName("Link")
    val url: String?,
    @SerializedName("Title")
    val title: String?,
    @SerializedName("SubContent")
    val description: String?,
    @SerializedName("Thumb")
    val iconUrl: String?,
    @SerializedName("Date")
    val dateStr: String?,
)