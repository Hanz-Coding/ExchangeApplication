package com.plcoding.goldchart.gold.data.mappers

import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetDto
import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetResponseDto
import com.plcoding.goldchart.gold.data.utils.validatePNJBrandType
import com.plcoding.goldchart.gold.data.utils.validatePNJName
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.domain.model.local.Currency
import com.plcoding.goldchart.gold.domain.model.local.CurrencyExchange
import com.plcoding.goldchart.gold.domain.model.local.CurrencyCompany

fun PNJAssetResponseDto.toDomain(): Currency {
    val company = CurrencyCompany(CompanyName.PNJ, this.updatetime)
    val assetList: List<CurrencyExchange> = this.data.map { dto -> dto.toDomain() }
    return Currency(company, assetList)
}

fun PNJAssetDto.toDomain(): CurrencyExchange {
    return CurrencyExchange(
        currencyCode = CompanyName.PNJ + code,
        currencyName = validatePNJName(code),
        currencyType = validatePNJBrandType(code),
        companyName = CompanyName.PNJ,
        iconUrl = "",
        buy = mua.toDouble(),
        sell = ban.toDouble(),
        transfer = 0.0,
        previousTransfer = 0.0,
        previousSell = 0.0,
        previousBuy = 0.0
    )
}