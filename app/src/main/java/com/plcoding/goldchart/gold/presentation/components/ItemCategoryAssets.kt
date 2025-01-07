package com.plcoding.goldchart.gold.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.goldchart.gold.presentation.model.CurrencyExchangeUI

@Composable
fun ItemCategoryAssets(
    category: Any?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when (category) {
            is String -> ItemCategoryText(category)
            is CurrencyExchangeUI -> ItemPrice(category)
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