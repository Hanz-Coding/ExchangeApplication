package com.plcoding.goldchart.gold.data.mappers

import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCGoldHistoryResponseDto
import com.plcoding.goldchart.gold.data.utils.validateSJCName
import com.plcoding.goldchart.gold.domain.CompanyName
import java.time.LocalDate

fun SJCAssetResponseDto.toDomain(): Currency {

    val updateTime =
        latestDate?.let { getTime(latestDate, "HH:mm dd/MM/yyyy") } ?: (currentDate?.let {
            getTime(
                currentDate,
                "dd/MM/yyyy"
            )
        }) ?: LocalDate.now().toEpochDay()
    val company = Company(CompanyName.SJC, updateTime)

    val exchangeList: List<Exchange> = this.data.map { dto -> dto.toDomain() }
    return Currency(company, exchangeList)
}

fun SJCGoldHistoryResponseDto.toDomain(): Currency {
    val company = Company(CompanyName.SJC, LocalDate.now().toEpochDay())
    val exchangeList: List<Exchange>? = this.data?.map { dto -> dto.toDomain() }
    return Currency(company, exchangeList ?: emptyList())
}

fun SJCAssetDto.toDomain(): Exchange {
    return Exchange(
        currencyCode = CompanyName.SJC + id.toString(),
        currencyName = validateSJCName(CompanyName.SJC + id.toString()),
        currencyType = branchName,
        companyName = CompanyName.SJC,
        iconUrl = "",
        buy = buyValue,
        sell = sellValue,
        transfer = 0.0,
        previousTransfer = 0.0,
        previousSell = 0.0,
        previousBuy = 0.0
    )
}