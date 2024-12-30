package com.plcoding.goldchart.gold.data.network.retrofit

import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface PNJAssetApi {
    @GET("/api/get-gold-price")
    suspend fun fetchPNJAsset(
    ): Response<PNJAssetResponseDto>
}