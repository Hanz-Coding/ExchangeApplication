package com.plcoding.goldchart.exchange.data.dto.bidv

import com.google.gson.annotations.SerializedName

data class BIDVCurrencyResponseDto(
    @SerializedName("data")
    val `data`: List<BIDVDataDto>,
    @SerializedName("day_en")
    val day_en: String,
    @SerializedName("hour")
    val hour: String,
    @SerializedName("note_en")
    val note_en: String,
    @SerializedName("note_vi")
    val note_vi: String,
    @SerializedName("show_dmex")
    val show_dmex: String,
    @SerializedName("show_dmex_home")
    val show_dmex_home: String,
    @SerializedName("status")
    val status: Int,
)