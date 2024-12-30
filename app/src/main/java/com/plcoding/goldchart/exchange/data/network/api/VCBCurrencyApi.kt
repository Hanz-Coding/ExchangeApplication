package com.plcoding.goldchart.exchange.data.network.api

import com.plcoding.goldchart.exchange.data.dto.vcb.VCBCurrencyResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VCBCurrencyApi {
    @GET("/api/exchangerates")
    suspend fun fetchCurrency(
        @Query("date") date: String,
    ): Response<VCBCurrencyResponseDto>
}