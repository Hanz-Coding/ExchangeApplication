package com.plcoding.goldchart.gold.data.dto.sjc

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SJCAssetResponseDto(
    @SerializedName("data")
    val data: List<SJCAssetDto>,
    @SerializedName("latestDate")
    val latestDate: String?,
    @SerializedName("currentDate")
    val currentDate: String?,
    @SerializedName("success")
    val success: Boolean,
)