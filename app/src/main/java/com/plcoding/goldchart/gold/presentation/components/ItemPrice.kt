package com.plcoding.goldchart.gold.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.goldchart.gold.presentation.model.ExchangeUI

@Composable
fun ItemPrice(
    exchangeUI: ExchangeUI,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(8.dp)
            )
            .height(height = 80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally),
                text = exchangeUI.currencyName,
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
        ) {
            Text(
                text = exchangeUI.buy,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Default,
                letterSpacing = 0.5.sp
            )
            Text(
                text = exchangeUI.buyChange,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = exchangeUI.buyColor,
                letterSpacing = 0.5.sp
            )
            Text(
                text = exchangeUI.buyChangePercent,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = exchangeUI.buyColor,
                letterSpacing = 0.5.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
        ) {
            Text(
                text = exchangeUI.sell,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Default
            )

            Text(
                text = exchangeUI.sellChange,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = exchangeUI.sellColor
            )
            Text(
                text = exchangeUI.sellChangePercent,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = exchangeUI.sellColor
            )
        }
    }
}

@Preview
@Composable
private fun ItemPricePreview() {
    val exchangeDisplay = ExchangeUI(
        currencyName = "Vang SJC",
        buy = "85,700,000",
        buyChange = "800,000",
        buyChangePercent = "1%",
        buyColor = Color(0xff08A045),
        sell = "87,700,000",
        sellChange = "800,000",
        sellChangePercent = "1%",
        sellColor = Color(0xff08A045),
        backgroundColor = Color(0xffc7f9cc)
    )
    ItemPrice(exchangeUI = exchangeDisplay)
}

