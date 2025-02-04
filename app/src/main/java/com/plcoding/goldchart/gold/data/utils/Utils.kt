package com.plcoding.goldchart.gold.data.utils

fun validatePNJBrandType(name: String): String {
    return when {
        name.lowercase().contains("cantho") -> "Cần Thơ"
        name.lowercase().contains("hcm") -> "Hồ Chí Minh"
        name.lowercase().contains("hanoi") -> "Hà Nội"
        name.lowercase().contains("danang") -> "Đà Nẵng"
        name.lowercase().contains("gvnt") -> "Giá vàng nữ trang"
        else -> ""
    }
}

fun validatePNJName(title: String): String {
    return when {
        title.lowercase().contains("pnj") -> "PNJ"
        title.lowercase().contains("sjc") -> "SJC"
        title.lowercase().contains("gvnt-n24k") -> "Nhẫn Trơn 999.9"
        title.lowercase().contains("gvnt-24k") -> "Vàng nữ trang 24k"
        title.lowercase().contains("gvnt-18k") -> "Vàng nữ trang 18k"
        title.lowercase().contains("gvnt-14k") -> "Vàng nữ trang 14k"
        title.lowercase().contains("gvnt-10k") -> "Vàng nữ trang 10k"
        else -> ""
    }
}

fun validateSJCName(code: String): String {
    return when (code) {
        "SJC_1" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_17" -> "Vàng SJC 5 chỉ"
        "SJC_33" -> "Vàng SJC 0.5 chỉ, 1 chỉ, 2 chỉ"
        "SJC_49" -> "Vàng nhẫn SJC 99,99 1 chỉ, 2 chỉ, 5 chỉ"
        "SJC_65" -> "Vàng nhẫn SJC 99,99 0.5 chỉ, 0.3 chỉ"
        "SJC_81" -> "Nữ trang 99,99%"
        "SJC_97" -> "Nữ trang 99%"
        "SJC_113" -> "Nữ trang 75%"
        "SJC_129" -> "Nữ trang 68%"
        "SJC_210" -> "Nữ trang 61%"
        "SJC_145" -> "Nữ trang 58,3%"
        "SJC_161" -> "Nữ trang 41,7%"
        "SJC_2" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_13" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_177" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_188" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_7" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_10" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_4" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_8" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_9" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_16" -> "Vàng SJC 1L, 10L, 1KG"
        "SJC_5" -> "Vàng SJC 1L, 10L, 1KG"
        else -> "Unknown"
    }
}