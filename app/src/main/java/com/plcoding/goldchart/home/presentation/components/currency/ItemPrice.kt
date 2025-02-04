package com.plcoding.goldchart.home.presentation.components.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.plcoding.goldchart.home.presentation.model.PriceUI

@Composable
fun ItemPrice(priceUI: PriceUI) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = context.getString(priceUI.title),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = context.getString(priceUI.des),
                color = Color.Gray,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = priceUI.value,
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = priceUI.valueChange,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = priceUI.color
            )
            Text(
                text = priceUI.valueChangePercent,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = priceUI.color
            )
        }
    }
}

@Preview
@Composable
private fun PreviewItemPrice() {
}