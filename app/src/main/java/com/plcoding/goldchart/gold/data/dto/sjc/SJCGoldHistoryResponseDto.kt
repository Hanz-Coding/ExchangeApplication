package com.plcoding.goldchart.gold.data.dto.sjc

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SJCGoldHistoryResponseDto(
    @SerializedName("data")
    val data: List<SJCAssetDto>? = null,
)