package com.plcoding.goldchart.gold.presentation.gold_price.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ItemPrice(
//    assetsUI: AssetsUI,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1.5f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally),
                text = "assetsUI.title",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
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
                text = "assetsUI.assets.buyPrice.formatted",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light
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
                text = "assetsUI.assets.sellPrice.formatted",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light
            )
            Text(text = "-12%", color = Color.Gray)
        }
    }
}

@Preview
@Composable
private fun ItemPricePreview() {
}

