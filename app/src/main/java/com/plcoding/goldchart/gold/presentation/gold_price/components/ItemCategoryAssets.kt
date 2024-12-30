package com.plcoding.goldchart.gold.presentation.gold_price.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ItemCategoryAssets(
    category: Any?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        when (category) {
            is String -> Text(
                text = category ?: "Giá vàng",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Green
            )

//            is AssetsUI -> ItemPrice(category)
        }
    }
}

@Preview
@Composable
private fun CategoryAssetsPreview() {
    ItemCategoryAssets(
        category = "Ho Chi Minh",
    )
}