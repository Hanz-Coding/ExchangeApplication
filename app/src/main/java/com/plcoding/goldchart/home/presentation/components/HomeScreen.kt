@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.plcoding.goldchart.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.goldchart.home.presentation.state.HomeState
import com.plcoding.goldchart.home.presentation.viewmodel.HomeViewModel
import com.plcoding.goldchart.home.presentation.components.currency.ItemCurrency
import com.plcoding.goldchart.home.presentation.components.news.NewsBottomSheet
import com.plcoding.goldchart.home.presentation.components.news.NewsItems
import com.plcoding.goldchart.home.presentation.model.CurrencyUI
import com.plcoding.goldchart.home.presentation.model.NewsUI
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot() {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(state)
}

@Composable
private fun HomeScreen(
    state: HomeState,
) {
    var selectedNews by rememberSaveable { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 90.dp)
            .verticalScroll(scrollState)
    ) {
        val listItem = state.listItem.keys.sorted()
        listItem.onEach { index ->
            when (state.listItem[index]) {
                is CurrencyUI -> {
                    val goldUI = state.listItem[index] as CurrencyUI
                    ItemCurrency(
                        currencyUI = goldUI,
                        onClick = { })
                }

                is NewsUI -> {
                    val newsUI = state.listItem[index] as NewsUI
                    NewsItems(newsUI,
                        onItemClick = {
                            selectedNews = it
                            scope.launch {
                                isBottomSheetVisible = true
                                sheetState.expand()
                            }
                        })
                }

                else -> {
                }
            }
        }
    }

    NewsBottomSheet(
        isBottomSheetVisible = isBottomSheetVisible,
        sheetState = sheetState,
        url = selectedNews,
        onDismiss = {
            scope.launch { sheetState.hide() }
                .invokeOnCompletion { isBottomSheetVisible = false }
        }
    )
}