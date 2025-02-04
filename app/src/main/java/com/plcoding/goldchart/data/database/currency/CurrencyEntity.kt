package com.plcoding.goldchart.data.database.currency

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class CurrencyEntity(
    @Embedded
    val company: CompanyEntity,
    @Relation(
        parentColumn = "company_name",
        entityColumn = "company_name"
    )

    val exchangeList: List<ExchangeEntity>,
)
