package com.plcoding.goldchart.core.data.database.currency

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDAO {
    @Upsert
    suspend fun upsertExchangeList(currencyList: List<CurrencyExchangeEntity>)

    @Upsert
    suspend fun upsertCompany(company: CurrencyCompanyEntity)

    @Query("SELECT * FROM CurrencyCompanyEntity WHERE company_name = :companyName")
    fun getCurrencyByCompanyName(companyName: String): Flow<CurrencyEntity>

    @Query("SELECT * FROM CurrencyCompanyEntity WHERE company_name = :companyName")
    suspend fun getCompanyByName(companyName: String): CurrencyCompanyEntity?
}