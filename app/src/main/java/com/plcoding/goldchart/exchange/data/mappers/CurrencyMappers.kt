package com.plcoding.goldchart.exchange.data.mappers

import com.plcoding.goldchart.data.mappers.generateCurrencyId
import com.plcoding.goldchart.domain.model.Company
import com.plcoding.goldchart.domain.model.Currency
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.exchange.data.Constant
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.dto.bidv.BIDVDataDto
import com.plcoding.goldchart.exchange.data.dto.vcb.VCBCurrencyResponseDto
import com.plcoding.goldchart.exchange.data.dto.vcb.VCBDataDto
import com.plcoding.goldchart.exchange.domain.CompanyName
import java.text.SimpleDateFormat
import java.util.Locale

fun VCBDataDto.toDomain(): Exchange {
    return Exchange(
        currencyId = generateCurrencyId(CompanyName.VCB, currencyCode ?: ""),
        currencyCode = currencyCode ?: "",
        currencyName = currencyName ?: "",
        currencyType = currencyCode ?: "",
        companyName = CompanyName.VCB,
        iconUrl = generateIconUrl(CompanyName.VCB, icon ?: ""),
        buy = buy?.toDouble() ?: 0.0,
        sell = sell?.toDouble() ?: 0.0,
        transfer = transfer?.toDouble() ?: 0.0,
        previousTransfer = 0.0,
        previousSell = 0.0,
        previousBuy = 0.0
    )
}

fun VCBCurrencyResponseDto.toDomain(): Currency {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val updatedDate = this.updatedDate?.let { dateFormat.parse(it) }
    val updateTime = updatedDate?.time ?: 0L
    val company = Company(CompanyName.VCB, updateTime)

    return Currency(
        company,
        this.data?.map { dto ->
            dto.toDomain()
        } ?: emptyList()
    )
}

fun BIDVCurrencyResponseDto.toDomain(): Currency {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val remoteDate = this.day_en + " " + this.hour
    val updatedDate = dateFormat.parse(remoteDate)
    val updateTime = updatedDate?.time ?: 0L
    val company = Company(CompanyName.BIDV, updateTime)

    return Currency(company,
        this.data.map { dto ->
            dto.toDomain()
        })
}


fun BIDVDataDto.toDomain(): Exchange {
    return Exchange(
        currencyId = generateCurrencyId(CompanyName.BIDV, currencyCode ?: ""),
        currencyCode = currencyCode ?: "",
        currencyName = nameVI ?: "",
        currencyType = currencyCode ?: "",
        companyName = CompanyName.BIDV,
        iconUrl = generateIconUrl(CompanyName.BIDV, image ?: ""),
        buy = convertStringToDouble(buy ?: ""),
        sell = convertStringToDouble(sell ?: ""),
        transfer = convertStringToDouble(transfer ?: ""),
        previousTransfer = 0.0,
        previousSell = 0.0,
        previousBuy = 0.0
    )
}

fun convertStringToDouble(input: String): Double {
    return if (input.contains("-")) {
        0.0
    } else {
        input.replace(",", "").toDouble()
    }
}

fun generateIconUrl(companyName: String, iconUrl: String): String {
    return when (companyName) {
        CompanyName.VCB -> Constant.BASE_URL_VCB + iconUrl
        CompanyName.BIDV -> Constant.BASE_URL_BIDV + iconUrl
        else -> iconUrl
    }
}
