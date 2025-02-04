package com.plcoding.goldchart.home.data.network.api

import com.plcoding.goldchart.home.data.network.dto.NewsResponseDto
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsApi {
    @POST("/du-lieu/ajax/GoldNews/GoldRelNews.ashx")
    suspend fun fetchNews(
        @Query("Type") type: String = "NEWS",
        @Query("PageIndex") pageIndex: Int = 1,
        @Query("PageSize") pageSize: Int = 20,
    ): Response<NewsResponseDto>
}