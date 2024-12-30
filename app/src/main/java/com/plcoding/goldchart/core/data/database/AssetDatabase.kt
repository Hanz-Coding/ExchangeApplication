package com.plcoding.goldchart.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.goldchart.core.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.core.data.database.currency.CurrencyExchangeEntity
import com.plcoding.goldchart.core.data.database.currency.CurrencyCompanyEntity

@Database(
    entities = [CurrencyExchangeEntity::class, CurrencyCompanyEntity::class],
    version = 1
)
abstract class AssetDatabase : RoomDatabase() {
    abstract val currencyDAO: CurrencyDAO

    companion object {
        const val DB_NAME = "asset.db"
    }
}