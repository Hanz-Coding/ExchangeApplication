package com.plcoding.goldchart.gold.data.dto.pnj

import com.google.gson.annotations.SerializedName

data class PNJAssetResponseDto(
    @SerializedName("data")
    val data: List<PNJAssetDto>,
    @SerializedName("updatetime")
    val updateTime: Long
)
