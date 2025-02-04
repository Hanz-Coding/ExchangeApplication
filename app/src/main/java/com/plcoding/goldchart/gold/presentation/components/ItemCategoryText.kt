package com.plcoding.goldchart.gold.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.goldchart.R

@Composable
fun ItemCategoryTitle(category: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 6.dp, bottom = 4.dp, end = 12.dp)
    ) {
        val context = LocalContext.current
        Text(
            text = category ?: context.getString(R.string.category_gold),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}

@Preview
@Composable
private fun CategoryAssetsPreview() {
    ItemCategory(
        category = "Ho Chi Minh",
    )
}