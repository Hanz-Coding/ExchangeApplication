package com.plcoding.goldchart.exchange.presentation.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Scale
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.exchange.presentation.getCurrencyNameByCode

@Composable
fun ItemCurrencyExchange(
    exchange: Exchange,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(8.dp)
            )
            .height(height = 80.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                println("Log image ${exchange.iconUrl}")
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(exchange.iconUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 42.dp, height = 24.dp)
                        .padding(start = 8.dp, end = 2.dp)
                )
                Text(
                    text = exchange.currencyCode,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = getCurrencyNameByCode(exchange.currencyCode),
                fontSize = 10.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 2.dp),
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (exchange.buy != 0.0) {
                ItemCurrencyPrice(
                    exchange.buy,
                    exchange.previousBuy
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (exchange.transfer != 0.0) {
                ItemCurrencyPrice(
                    exchange.transfer,
                    exchange.previousTransfer,
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (exchange.sell != 0.0) {
                ItemCurrencyPrice(
                    exchange.sell,
                    exchange.previousSell,

                    )
            }
        }
    }
}
