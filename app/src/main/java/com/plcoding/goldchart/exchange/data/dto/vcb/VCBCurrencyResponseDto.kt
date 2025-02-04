package com.plcoding.goldchart.exchange.data.dto.vcb

import com.google.gson.annotations.SerializedName

data class VCBCurrencyResponseDto(
    @SerializedName("Count")
    val count: Int?,
    @SerializedName("Data")
    val data: List<VCBDataDto>?,
    @SerializedName("Date")
    val date: String?,
    @SerializedName("UpdatedDate")
    val updatedDate: String?
)
