package com.plcoding.goldchart.home.presentation.components.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.plcoding.goldchart.home.presentation.model.NewsUI

@Composable
fun NewsItems(
    news: NewsUI,
    onItemClick: (url: String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(news.url) }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = news.iconUrl,
                contentDescription = news.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = news.title,
                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )

            Text(
                text = news.description,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 3,
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
            )

            Text(
                text = news.dateStr,
                fontFamily = FontFamily.Default,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .align(Alignment.End)
            )

        }
    }
}

@Preview
@Composable
private fun ItemNewsPreview() {
    NewsItems(
        news = NewsUI(
            url = "https://cafef.vn/thi-truong-ngay-221-dau-giam-vang-cao-nhat-2-thang-quang-sat-cao-nhat-1-thang-188250122072102847.chn",
            title = "Thị trường ngày 22/1:  Dầu giảm, vàng cao nhất 2 tháng, quặng sắt cao nhất 1 tháng",
            description = "Chốt phiên giao dịch ngày 21/1/2025, dầu giảm sau khi Tổng thống Mỹ Donald Trump tuyên bố tình trạng khẩn cấp năng lượng quốc gia. Giá vàng đạt mức cao nhất trong hơn 2 tháng do rủi ro từ chính sách của ông Trump và đồng USD yếu.",
            iconUrl = "https://cafefcdn.com/203337114487263232/2025/1/22/avatar1737505165040-17375051659241023355865.jpg",
            dateStr = "18:24:00 21/01/2025"
        ),
        onItemClick = {}
    )
}