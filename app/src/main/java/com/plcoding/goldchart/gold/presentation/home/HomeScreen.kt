package com.plcoding.goldchart.gold.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.goldchart.gold.domain.AssetType
import com.plcoding.goldchart.gold.domain.CompanyName
import com.plcoding.goldchart.gold.presentation.home.components.AssetsCard
import com.plcoding.goldchart.gold.presentation.mappers.toDisplay

@Composable
fun HomeScreenRoot(
    viewModel: AssetsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    println("log HomeScreenRoot")

    HomeScreen(state)
}

@Composable
private fun HomeScreen(
    state: AssetState,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        val sjcAsset = state.currencyMap[CompanyName.SJC]
        AssetsCard(
            currencyDisplay = sjcAsset?.toDisplay(AssetType.SJC),
            onClick = { })

        AssetsCard(
            currencyDisplay = sjcAsset?.toDisplay(AssetType.SJC_RING),
            onClick = { })
    }
}