package com.plcoding.goldchart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.goldchart.data.database.currency.CurrencyDAO
import com.plcoding.goldchart.data.database.currency.ExchangeEntity
import com.plcoding.goldchart.data.database.currency.CompanyEntity
import com.plcoding.goldchart.data.database.news.NewsEntity

@Database(
    entities = [ExchangeEntity::class, CompanyEntity::class, NewsEntity::class ],
    version = 1
)
abstract class AssetDatabase : RoomDatabase() {
    abstract val currencyDAO: CurrencyDAO

    companion object {
        const val DB_NAME = "asset.db"
    }
}