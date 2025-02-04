package com.plcoding.goldchart.data.database.currency

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.plcoding.goldchart.data.database.news.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDAO {
    @Upsert
    suspend fun upsertExchangeList(currencyList: List<ExchangeEntity>)

    @Upsert
    suspend fun upsertCompany(company: CompanyEntity)

    @Query("SELECT * FROM CompanyEntity WHERE company_name = :companyName")
    suspend fun getCurrency(companyName: String): CurrencyEntity?

    @Query("SELECT * FROM CompanyEntity WHERE company_name = :companyName")
    fun getCurrencyByCompanyName(companyName: String): Flow<CurrencyEntity>

    @Query("SELECT * FROM CompanyEntity WHERE company_name = :companyName")
    suspend fun getCompanyByName(companyName: String): CompanyEntity?

    @Upsert
    suspend fun upsertAll(newsEntity: List<NewsEntity>)

    @Query("SELECT * FROM NewsEntity")
    fun getAll(): Flow<List<NewsEntity>>

}