package com.plcoding.goldchart.exchange.data.dto.bidv

import com.google.gson.annotations.SerializedName

data class BIDVDataDto(
    @SerializedName("ban")
    val sell: String?,
    @SerializedName("currency")
    val currencyCode: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("muaCk")
    val transfer: String?,
    @SerializedName("muaTm")
    val buy: String?,
    @SerializedName("nameEN")
    val nameEN: String?,
    @SerializedName("nameVI")
    val nameVI: String?,
)
