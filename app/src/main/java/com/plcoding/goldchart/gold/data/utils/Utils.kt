package com.plcoding.goldchart.gold.data.utils

fun validatePNJBrandName(name: String): String {
    return when {
        name.lowercase().contains("cantho") -> "Cần Thơ"
        name.lowercase().contains("hcm") -> "Hồ Chí Minh"
        name.lowercase().contains("hanoi") -> "Hà Nội"
        name.lowercase().contains("danang") -> "Đà Nẵng"
        else -> ""
    }
}

fun validatePNJTitle(title: String): String {
    return when {
        title.lowercase().contains("pnj") -> "PNJ"
        title.lowercase().contains("sjc") -> "SJC"
        else -> ""
    }
}