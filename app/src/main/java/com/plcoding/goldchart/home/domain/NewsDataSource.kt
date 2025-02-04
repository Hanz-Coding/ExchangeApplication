package com.plcoding.goldchart.home.domain

import com.plcoding.goldchart.core.domain.utils.DataError
import com.plcoding.goldchart.core.domain.utils.Result

interface NewsDataSource {
    suspend fun fetchNews(): Result<List<News>, DataError.RemoteError>
}