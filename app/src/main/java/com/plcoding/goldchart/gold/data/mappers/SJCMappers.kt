package com.plcoding.goldchart.gold.data.mappers

import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import com.plcoding.goldchart.gold.data.utils.validateSJCName
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.domain.model.local.Currency
import com.plcoding.goldchart.gold.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.gold.domain.model.local.CurrencyCompany
import java.time.LocalDate

fun SJCAssetResponseDto.toDomain(): Currency {

    val updateTime =
        latestDate?.let { getTime(latestDate, "HH:mm dd/MM/yyyy") } ?: (currentDate?.let {
            getTime(
                currentDate,
                "dd/MM/yyyy"
            )
        }) ?: LocalDate.now().toEpochDay()
    val company = CurrencyCompany(CompanyName.SJC, updateTime)

    val exchangeList: List<CurrencyExchange> = this.data.map { dto -> dto.toDomain() }
    return Currency(company, exchangeList)
}

fun SJCAssetDto.toDomain(): CurrencyExchange {
    return CurrencyExchange(
        currencyCode = CompanyName.SJC + Id.toString(),
        currencyName = validateSJCName(CompanyName.SJC + Id.toString()),
        currencyType = BranchName,
        companyName = CompanyName.SJC,
        iconUrl = "",
        buy = BuyValue,
        sell = SellValue,
        transfer = 0.0,
        previousTransfer = 0.0,
        previousSell = 0.0,
        previousBuy = 0.0
    )
}