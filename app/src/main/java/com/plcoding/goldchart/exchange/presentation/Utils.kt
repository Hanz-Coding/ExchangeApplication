package com.plcoding.goldchart.exchange.presentation

fun getCurrencyNameByCode(code: String): String {
    return when (code) {
        "USD" -> "DOLLAR Mỹ"
        "JPY" -> "YÊN Nhật"
        "GBP" -> "BẢNG Anh"
        "EUR" -> "EURO"
        "KRW" -> "WON Hàn Quốc"
        "SGD" -> "DOLLAR Singapore"
        "CNY" -> "Nhân Dan Tệ (TQ)"
        "RUB" -> "RUB Nga"
        "BAHT" -> "BAHT Thái Lan"
        "AUD" -> "AUSTRALIAN Dollar"
        "CAD" -> "DOLLAR Canada"
        "CHF" -> "FRANC Thuỵ Sỹ"
        "DKK" -> "KRONE Đan Mạch"
        "HKD" -> "DOLLAR Hong Kong"
        "INR" -> "RUPEE Ấn Độ"
        "KWD" -> "KUWAITI DINAR"
        "MYR" -> "RINGGIT Malaysia"
        "NOK" -> "KRONER Na Uy"
        "SAR" -> "RIAL Ả Rập"
        "SEK" -> "KRONA Thuỵ Điển"
        else -> "Unknown"
    }
}