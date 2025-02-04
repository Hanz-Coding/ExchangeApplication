package com.plcoding.goldchart.data.database.currency

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExchangeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "code_name") val currencyCodeName: String,
    @ColumnInfo(name = "currency_code") val currencyCode: String,
    @ColumnInfo(name = "currency_name") val currencyName: String,
    @ColumnInfo(name = "currency_type") val currencyType: String,
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "icon_url") val iconUrl: String,
    @ColumnInfo(name = "buy") val buy: Double,
    @ColumnInfo(name = "transfer") val transfer: Double,
    @ColumnInfo(name = "sell") val sell: Double,
    @ColumnInfo(name = "previous_buy") val previousBuy: Double,
    @ColumnInfo(name = "previous_transfer") val previousTransfer: Double,
    @ColumnInfo(name = "previous_sell") val previousSell: Double,
)
