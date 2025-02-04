package com.plcoding.goldchart.gold.data.dto.sjc

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SJCAssetDto(
    @SerializedName("BranchName")
    val branchName: String,
    @SerializedName("Buy")
    val buy: String,
    @SerializedName("BuyDifferValue")
    val buyDifferValue: Int,
    @SerializedName("BuyValue")
    val buyValue: Double,
    @SerializedName("GroupDate")
    val groupDate: String,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Sell")
    val sell: String,
    @SerializedName("SellDifferValue")
    val sellDifferValue: Int,
    @SerializedName("SellValue")
    val sellValue: Double,
    @SerializedName("TypeName")
    val typeName: String,
)
