@file:OptIn(ExperimentalMaterial3Api::class)

package com.plcoding.goldchart.exchange.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.presentation.ExchangeMenuView
import com.plcoding.goldchart.exchange.presentation.ExchangeState

@Composable
fun VCBExchangeScreenRoot(
    state: ExchangeState,
    onRefreshTrigger: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val vcbCurrency = state.currencyMap[CompanyName.VCB]
        if (vcbCurrency != null) {
            ExchangeMenuView(vcbCurrency.company)

            PullToRefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = onRefreshTrigger,
                contentAlignment = Alignment.TopStart,
                modifier = modifier
            ) {
                println("PullToRefreshBox isRefreshing $state.isLoading")
                LazyColumn {
                    items(vcbCurrency.exchangeList) {
                        ItemCurrency(it)
                    }
                }
            }
        }
    }
}
