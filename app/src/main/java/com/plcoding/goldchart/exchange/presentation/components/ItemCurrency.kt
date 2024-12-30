package com.plcoding.goldchart.exchange.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Scale
import com.plcoding.goldchart.exchange.presentation.model.CurrencyExchangeUI

@Composable
fun ItemCurrency(
    exchange: CurrencyExchangeUI,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                println("Log iamge ${exchange.iconUrl}")
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(exchange.iconUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .scale(Scale.FILL)  // Chỉnh tỉ lệ ảnh (tuỳ chọn)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 42.dp, height = 24.dp)
                        .padding(end = 2.dp)
                )
                Text(
                    text = exchange.currencyCode,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = exchange.currencyName,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Light,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        PriceView(
            exchange.buy,
            exchange.previousBuy,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        PriceView(
            exchange.transfer,
            exchange.previousTransfer,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        PriceView(
            exchange.sell,
            exchange.previousSell,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun PriceView(
    value: Double,
    previousValue: Double,
    modifier: Modifier = Modifier,
) {
    val change = value - previousValue
    val changePercent = if (change == 0.0 || previousValue == 0.0) 0.0 else {
        change * 100 / previousValue
    }

    val textColor = if (change > 0) {
        Color.Green
    } else {
        Color.Red
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "%.2f".format(change),
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
        Text(
            text = "%.2f".format(changePercent) + "%",
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}

