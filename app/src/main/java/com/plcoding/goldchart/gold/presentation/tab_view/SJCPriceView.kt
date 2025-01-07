package com.plcoding.goldchart.gold.presentation.tab_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.goldchart.gold.presentation.CategoryState
import com.plcoding.goldchart.gold.presentation.GoldPriceViewModel
import com.plcoding.goldchart.gold.presentation.components.ItemCategoryAssets
import com.plcoding.goldchart.gold.presentation.components.ItemMenuView
import org.koin.androidx.compose.koinViewModel

@Composable
fun SJCPriceViewRoot() {
    val viewModel = koinViewModel<GoldPriceViewModel>()
    val state by viewModel.stateSJC.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                ItemMenuView(state.currencyCompany)
                SJCPriceView(state)
            }
        }
    }
}

@Composable
fun SJCPriceView(state: CategoryState) {
    LazyColumn {
        val assetUIList = state.categoryAssetList
        items(assetUIList) { category ->
            ItemCategoryAssets(category)
        }
    }
}

@Preview
@Composable
private fun SJCPricePreview() {

}

