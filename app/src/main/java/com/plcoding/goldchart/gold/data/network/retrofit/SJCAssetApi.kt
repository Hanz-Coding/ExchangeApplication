package com.plcoding.goldchart.gold.data.network.retrofit

import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface SJCAssetApi {
    @GET("/GoldPrice/Services/PriceService.ashx")
    suspend fun getAssetSJC(
    ): Response<SJCAssetResponseDto>
}