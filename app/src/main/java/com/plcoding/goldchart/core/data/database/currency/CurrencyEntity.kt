package com.plcoding.goldchart.core.data.database.currency

import androidx.room.Embedded
import androidx.room.Relation

data class CurrencyEntity(
    @Embedded
    val company: CurrencyCompanyEntity,
    @Relation(
        parentColumn = "company_name",
        entityColumn = "company_name"
    )

    val exchangeList: List<CurrencyExchangeEntity>,
)
