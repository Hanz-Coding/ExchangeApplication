package com.plcoding.goldchart.gold.data.mappers

import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetDto
import com.plcoding.goldchart.gold.data.dto.pnj.PNJAssetResponseDto
import com.plcoding.goldchart.gold.data.utils.validatePNJBrandType
import com.plcoding.goldchart.gold.data.utils.validatePNJName
import com.plcoding.goldchart.gold.domain.CompanyName

fun PNJAssetResponseDto.toDomain(): Currency {
    val company = Company(CompanyName.PNJ, this.updatetime)
    val assetList: List<Exchange> = this.data.map { dto -> dto.toDomain() }
    return Currency(company, assetList)
}

fun PNJAssetDto.toDomain(): Exchange {
    return Exchange(
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