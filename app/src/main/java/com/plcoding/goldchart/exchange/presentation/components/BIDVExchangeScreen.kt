package com.plcoding.goldchart.exchange.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.presentation.ExchangeViewModel
import com.plcoding.goldchart.exchange.presentation.components.item.ItemCurrencyExchange
import com.plcoding.goldchart.exchange.presentation.components.item.ItemMenuView
import org.koin.androidx.compose.koinViewModel

@Composable
fun BIDVExchangeScreenRoot() {
    val viewModel = koinViewModel<ExchangeViewModel>()
    val state by viewModel.stateBIDV.collectAsStateWithLifecycle()
    val currencyMap = state.currencyMap

    Column(modifier = Modifier.fillMaxSize()) {
        val currency = currencyMap[CompanyName.BIDV]
        if (currency != null) {
            ItemMenuView(currency.company)
            LazyColumn {
                items(currency.exchangeList) {
                    ItemCurrencyExchange(it)
                }
            }
        }
    }
}
