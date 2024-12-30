package com.plcoding.goldchart.core.data.database.currency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchangeList(currencyList: List<CurrencyExchangeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: CurrencyCompanyEntity)

    @Query("SELECT * FROM CurrencyCompanyEntity WHERE company_name = :companyName")
    fun getCurrencyByCompanyName(companyName: String): Flow<CurrencyEntity>

    @Query("SELECT * FROM CurrencyCompanyEntity WHERE company_name = :companyName")
    suspend fun getCompanyByName(companyName: String): CurrencyCompanyEntity?
}