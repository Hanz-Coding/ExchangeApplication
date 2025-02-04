package com.plcoding.goldchart.exchange.data.dto.vcb

import com.google.gson.annotations.SerializedName

data class VCBDataDto(
    @SerializedName("cash")
    val buy: String?,
    @SerializedName("currencyCode")
    val currencyCode: String?,
    @SerializedName("currencyName")
    val currencyName: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("sell")
    val sell: String?,
    @SerializedName("transfer")
    val transfer: String?
)
