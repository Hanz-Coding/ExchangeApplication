package com.plcoding.goldchart.gold.data.dto.sjc

import kotlinx.serialization.Serializable

@Serializable
data class SJCAssetResponseDto(
    val `data`: List<SJCAssetDto>,
    val latestDate: String?,
    val currentDate: String?,
    val success: Boolean,
)