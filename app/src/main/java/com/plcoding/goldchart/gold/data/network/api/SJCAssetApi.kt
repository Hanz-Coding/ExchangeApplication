package com.plcoding.goldchart.gold.data.network.api

import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCGoldHistoryResponseDto
import com.plcoding.goldchart.gold.data.network.method.SJCBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SJCAssetApi {

    @POST("/GoldPrice/Services/PriceService.ashx")
    suspend fun getAssetSJC(): Response<SJCAssetResponseDto>

    @FormUrlEncoded
    @POST("/GoldPrice/Services/PriceService.ashx")
    suspend fun getAssetSJCField(
        @Field("method") method: String,
        @Field("toDate") toDate: String,
    ): Response<SJCAssetResponseDto>

    @FormUrlEncoded
    @POST("/GoldPrice/Services/PriceService.ashx")
    suspend fun getAssetSJCHistory(
        @Field("fromDate") fromDate: String,
        @Field("goldPriceId") id: Int,
        @Field("method") method: String = "GetGoldPriceHistory",
        @Field("toDate") toDate: String,
    ): Response<SJCGoldHistoryResponseDto>
}