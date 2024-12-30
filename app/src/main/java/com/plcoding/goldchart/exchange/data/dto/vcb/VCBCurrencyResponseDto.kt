package com.plcoding.goldchart.exchange.data.dto.vcb

data class VCBCurrencyResponseDto(
    val Count: Int,
    val Data: List<VCBDataDto>,
    val Date: String,
    val UpdatedDate: String
)
