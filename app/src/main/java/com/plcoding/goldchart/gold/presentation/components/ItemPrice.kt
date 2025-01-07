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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.goldchart.gold.presentation.model.CurrencyExchangeUI

@Composable
fun ItemPrice(
    exchangeUI: CurrencyExchangeUI,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(8.dp)
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
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally),
                text = exchangeUI.buy.formatedValue,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Default
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally),
                text = exchangeUI.sell.formatedValue,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Default
            )
        }
    }
}

@Preview
@Composable
private fun ItemPricePreview() {
}

