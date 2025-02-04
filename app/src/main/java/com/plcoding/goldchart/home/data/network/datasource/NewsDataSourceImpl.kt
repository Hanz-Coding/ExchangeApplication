package com.plcoding.goldchart.home.data.network.datasource

import com.plcoding.goldchart.core.data.networking.safeCallRetrofit
import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result
import com.plcoding.goldchart.core.domain.utils.map
import com.plcoding.goldchart.home.data.mappers.toDomain
import com.plcoding.goldchart.home.data.network.api.NewsApi
import com.plcoding.goldchart.home.data.network.dto.NewsResponseDto
import com.plcoding.goldchart.home.domain.News
import com.plcoding.goldchart.home.domain.NewsDataSource

class NewsDataSourceImpl(val api: NewsApi) : NewsDataSource {
    override suspend fun fetchNews(): Result<List<News>, DataError.RemoteError> {
        return safeCallRetrofit<NewsResponseDto> {
            api.fetchNews()
        }.map { response ->
            response.newsList?.map { news -> news.toDomain() }
                ?: emptyList()
        }
    }
}