package com.plcoding.goldchart.home.data.network.repository

import com.plcoding.goldchart.core.domain.utils.onSuccess
import com.plcoding.goldchart.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.home.data.mappers.toEntity
import com.plcoding.goldchart.home.domain.News
import com.plcoding.goldchart.home.domain.NewsDataSource
import com.plcoding.goldchart.home.domain.NewsRepository
import com.plcoding.goldchart.home.presentation.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val dataSource: NewsDataSource,
    private val newsDAO: CurrencyDAO,
) : NewsRepository {
    override suspend fun fetchAndSaveNews() {
        dataSource.fetchNews().onSuccess { newsList ->
            newsDAO.upsertAll(newsList.map { it.toEntity() })
        }
    }

    override fun getNews(): Flow<List<News>> {
        return newsDAO.getAll().map { list -> list.map { it.toDomain() } }
    }
}