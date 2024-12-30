package com.plcoding.goldchart.exchange.data.network.api

import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVCurrencyResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface BIDVCurrencyApi {
    @GET("ServicesBIDV/ExchangeDetailServlet")
    suspend fun fetchCurrency(): Response<BIDVCurrencyResponseDto>
}