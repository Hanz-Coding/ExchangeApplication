package com.plcoding.goldchart.exchange.presentation.components.item

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ItemCurrencyPrice(
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