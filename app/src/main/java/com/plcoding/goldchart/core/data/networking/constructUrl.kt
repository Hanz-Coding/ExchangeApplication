package com.plcoding.goldchart.core.data.networking


fun constructUrl(url: String, baseUrl: String): String {
    return when {
        url.contains(baseUrl) -> url
        url.startsWith("/") -> baseUrl + url.drop(1)
        else -> baseUrl + url
    }
}