package com.plcoding.goldchart.gold.presentation.gold_price

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.goldchart.gold.presentation.gold_price.components.ItemCategoryAssets
import org.koin.androidx.compose.koinViewModel

@Composable
fun PNJPriceViewRoot(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<GoldPriceViewModel>()
    val state by viewModel.statePNJ.collectAsStateWithLifecycle()
    PNJPriceView(state)
}

@Composable
fun PNJPriceView(state: CategoryState) {
    LazyColumn {
        val assetUIList = state.categoryAssetList
        items(assetUIList) { category ->
            ItemCategoryAssets(category)
        }
    }
}

@Preview
@Composable
private fun PNJPricePreview() {

}