package com.plcoding.goldchart.exchange.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.goldchart.exchange.presentation.components.item.ItemCurrencyExchange
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.presentation.ExchangeMenuView
import com.plcoding.goldchart.exchange.presentation.state.ExchangeState
import com.plcoding.goldchart.exchange.presentation.ExchangeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BIDVExchangeScreenRoot(
    state: ExchangeState,
) {
    val viewModel = koinViewModel<ExchangeViewModel>()
    viewModel.fetchAndLoadCurrencyBIDV()

    Column(modifier = Modifier.fillMaxSize()) {
        val currency = state.currencyMap[CompanyName.BIDV]
        if (currency != null) {
            ExchangeMenuView(currency.company)
            LazyColumn {
                items(currency.exchangeList) {
                    ItemCurrencyExchange(it)
                }
            }
        }
    }
}
