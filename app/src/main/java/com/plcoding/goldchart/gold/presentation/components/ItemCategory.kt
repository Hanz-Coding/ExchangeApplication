package com.plcoding.goldchart.gold.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.goldchart.domain.model.Exchange
import com.plcoding.goldchart.gold.presentation.mappers.toDisplay

@Composable
fun ItemCategory(
    category: Any?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when (category) {
            is String -> ItemCategoryTitle(category)
            is Exchange -> ItemPrice(category.toDisplay())
        }
    }
}