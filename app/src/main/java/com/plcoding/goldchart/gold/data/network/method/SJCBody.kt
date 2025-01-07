package com.plcoding.goldchart.gold.data.network.method

import com.plcoding.goldchart.gold.domain.datasource.RequestBody

data class SJCBody(
    val method: String,
    val toDate: String, // 02/01/2025
) : RequestBody
