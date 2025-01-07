package com.plcoding.goldchart.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.goldchart.gold.presentation.model.CurrencyUI
import com.plcoding.goldchart.home.presentation.model.CurrencyDisplay

@Composable
fun AssetsCard(
    currencyDisplay: CurrencyDisplay?,
    onClick: () -> Unit,
) {
    if (currencyDisplay == null) return
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = currencyDisplay.backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = currencyDisplay.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = currencyDisplay.updateTime,
                fontFamily = FontFamily.Default,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            ItemCurrencyPrice(
                "Bán ra",
                "Giá cửa hàng bán ra mỗi lượng (đ)",
                currencyDisplay.buy,
                currencyDisplay.buyChange,
                currencyDisplay.buyColor,
                currencyDisplay.buyChangePercent,
                currencyDisplay.buyColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            ItemCurrencyPrice(
                "Mua vào",
                "Giá cửa hàng thu vào mỗi lượng (đ)",
                currencyDisplay.sell,
                currencyDisplay.sellChange,
                currencyDisplay.sellColor,
                currencyDisplay.sellChangePercent,
                currencyDisplay.sellColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoCard() {
}
