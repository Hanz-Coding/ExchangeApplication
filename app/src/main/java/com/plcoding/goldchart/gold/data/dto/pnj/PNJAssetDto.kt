package com.plcoding.goldchart.gold.data.dto.pnj

import com.google.gson.annotations.SerializedName

data class PNJAssetDto(
    @SerializedName("ban")
    val ban: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("mua")
    val mua: Int,
    @SerializedName("updateDate")
    val updateDate: String,
    @SerializedName("updateTime")
    val updateTime: Long
)