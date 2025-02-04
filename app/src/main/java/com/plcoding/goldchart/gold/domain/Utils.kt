package com.plcoding.goldchart.gold.domain

object CompanyName {
    const val VCB = "VCB"
    const val SJC = "SJC"
    const val PNJ = "PNJ"
}

object AssetType {
    const val SJC = "SJC_1"
    const val SJC_RING = "SJC_49"
}

object SJCCode {
    const val SJC = 1
    const val SJC_RING = 49
}

fun getTitleCurrency(id: String): String {
    return when (id) {
        "SJC_1" -> "Vàng miếng SJC"
        "SJC_49" -> "Vàng nhẫn 9999 (99,99%)"
        "VCB_USD" -> "Tỷ giá USD của Vietcombank"
        else -> "Vàng"
    }
}
