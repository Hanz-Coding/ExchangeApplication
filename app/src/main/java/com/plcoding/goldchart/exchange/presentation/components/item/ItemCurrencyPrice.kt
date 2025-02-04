package com.plcoding.goldchart.exchange.presentation.components.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.goldchart.presentation.utils.toDisplayNumber
import com.plcoding.goldchart.presentation.utils.toDisplayPercent

@Composable
fun ItemCurrencyPrice(
    value: Double,
    previousValue: Double,
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

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Text(
            text = value.toDisplayNumber(),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = change.toDisplayPercent(),
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Default,
            fontSize = 10.sp,
            color = textColor,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = changePercent.toDisplayPercent() + "%",
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            fontFamily = FontFamily.Default,
            color = textColor,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}