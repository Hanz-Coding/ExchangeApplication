package com.plcoding.goldchart.core.data.database.currency

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyCompanyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "updated_time") val updatedTime: Long,
)
