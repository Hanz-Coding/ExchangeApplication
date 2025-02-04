@file:OptIn(ExperimentalMaterial3Api::class)

package com.plcoding.goldchart.exchange.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.presentation.ExchangeViewModel
import com.plcoding.goldchart.exchange.presentation.components.item.ItemCurrencyExchange
import com.plcoding.goldchart.exchange.presentation.components.item.ItemMenuView
import org.koin.androidx.compose.koinViewModel

@Composable
fun VCBExchangeScreenRoot(
    onRefreshTrigger: () -> Unit = {},
) {
    val viewModel = koinViewModel<ExchangeViewModel>()
    val state by viewModel.stateVCB.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 90.dp)
    ) {
        val vcbCurrency = state.currencyMap[CompanyName.VCB]
        if (vcbCurrency != null) {
            ItemMenuView(vcbCurrency.company)

            PullToRefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = onRefreshTrigger,
                contentAlignment = Alignment.TopStart,
            ) {
                println("PullToRefreshBox isRefreshing $state.isLoading")
                LazyColumn {
                    items(vcbCurrency.exchangeList) {
                        ItemCurrencyExchange(it)
                    }
                }
            }
        }
    }
}
