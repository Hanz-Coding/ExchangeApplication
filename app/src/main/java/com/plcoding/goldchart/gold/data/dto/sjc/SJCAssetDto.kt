package com.plcoding.goldchart.gold.data.dto.sjc

import kotlinx.serialization.Serializable

@Serializable
data class SJCAssetDto(
    val BranchName: String,
    val Buy: String,
    val BuyDifferValue: Int,
    val BuyValue: Double,
    val GroupDate: String,
    val Id: Int,
    val Sell: String,
    val SellDifferValue: Int,
    val SellValue: Double,
    val TypeName: String,
)
