package com.plcoding.goldchart.home.presentation.components

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemCurrencyPrice(
    title: String,
    description: String,
    value: String,
    valueChange: String,
    valueChangeColor: Color,
    valueChangePercent: String,
    valueChangePercentColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
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
                text = value,
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = valueChange,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = valueChangeColor
            )
            Text(
                text = valueChangePercent,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = valueChangePercentColor
            )
        }
    }
}