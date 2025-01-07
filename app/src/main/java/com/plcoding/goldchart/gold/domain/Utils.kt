package com.plcoding.goldchart.gold.domain

object CompanyName {
    const val SJC = "SJC"
    const val PNJ = "PNJ"
    const val BTMC = "BTMC"
}

object AssetType {
    const val SJC = "SJC1"
    const val SJC_RING = "SJC49"
}

fun getTitleForSJC(id: String): String {
    return when (id) {
        "SJC1" -> "Vàng miếng SJC"
        "SJC49" -> "Vàng nhẫn 9999 (99,99%)"
        else -> "Vàng"
    }
}
