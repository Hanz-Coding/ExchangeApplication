package com.plcoding.goldchart.exchange.data.dto.bidv

data class BIDVCurrencyResponseDto(
    val `data`: List<BIDVDataDto>,
    val day_en: String,
    val day_vi: String,
    val hour: String,
    val note_en: String,
    val note_vi: String,
    val show_dmex: String,
    val show_dmex_home: String,
    val status: Int
)