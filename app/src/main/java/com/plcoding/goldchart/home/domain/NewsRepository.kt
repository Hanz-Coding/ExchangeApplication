package com.plcoding.goldchart.home.domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun fetchAndSaveNews()
    fun getNews(): Flow<List<News>>
}