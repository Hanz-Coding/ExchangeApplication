package com.plcoding.goldchart.gold.domain

object CompanyName {
    const val SJC = "SJC"
    const val PNJ = "PNJ"
    const val BTMC = "BTMC"
}

object AssetType {
    const val SJC = 1
    const val SJC_RING = 49
}

fun getTitleForSJC(id: Int): String {
    return when (id) {
        1 -> "Vàng miếng SJC"
        49 -> "Vàng nhẫn 9999 (99,99%)"
        else -> "Vàng"
    }
}