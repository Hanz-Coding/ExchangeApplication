package com.plcoding.goldchart.gold.data.mappers

import com.plcoding.goldchart.data.mappers.generateCurrencyId
import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCAssetResponseDto
import com.plcoding.goldchart.gold.data.dto.sjc.SJCGoldHistoryResponseDto
import com.plcoding.goldchart.gold.data.utils.validateSJCName
import com.plcoding.goldchart.gold.domain.CompanyName
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

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
    val currencyId = generateCurrencyId(CompanyName.SJC, id.toString())
    return Exchange(
        currencyId = currencyId,
        currencyCode = id.toString(),
        currencyName = validateSJCName(currencyId),
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

private fun getTime(time: String, format: String): Long {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    val date = dateFormat.parse(time)
    return date?.time ?: 0L
}