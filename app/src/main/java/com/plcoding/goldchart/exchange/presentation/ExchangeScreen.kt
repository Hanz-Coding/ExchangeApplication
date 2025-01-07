package com.plcoding.goldchart.exchange.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.goldchart.exchange.data.mappers.formatLongToDate
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.presentation.components.BIDVExchangeScreenRoot
import com.plcoding.goldchart.exchange.presentation.components.VCBExchangeScreenRoot
import com.plcoding.goldchart.exchange.presentation.model.CurrencyCompanyUI
import com.plcoding.goldchart.exchange.presentation.state.ExchangeState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeScreenRoot(
    viewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val tabItems = listOf("Vietcombank", "BIDV")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
        // ScrollableTabRow tạo thanh tab có thể cuộn ngang
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex, // Chỉ định tab được chọn
            edgePadding = 16.dp // Padding bên ngoài các tab
        ) {
            tabItems.forEachIndexed { index, tab ->
                Tab(selected = false, // Xác định tab được chọn
                    onClick = {
                        /* Handle click event */
                        selectedTabIndex = index
                    }, text = { Text(text = tab) })
            }
        }
        // Nội dung của tab có thể được thay đổi khi tab được chọn
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxWidth()
        ) { index ->
            tabContents(index, state, viewModel::onPullToRefreshTrigger)
        }
    }
}

val tabContents: @Composable (Int, ExchangeState, (String) -> Unit) -> Unit =
    { index, state, onRefresh ->
        when (index) {
            0 -> {
                VCBExchangeScreenRoot(
                    state,
                    onRefreshTrigger = { onRefresh(CompanyName.VCB) }
                )
            }

            1 -> {
                BIDVExchangeScreenRoot(state)
            }
        }
    }

@Composable
fun ExchangeMenuView(companyUI: CurrencyCompanyUI) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val dateLong = companyUI.updatedTime
        val date = formatLongToDate(dateLong)
        Text(
            text = date,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(4.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ngoại tệ",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "Mua",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "CK",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "Bán",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview
@Composable
private fun ExchangeMenuPreview() {
//    ExchangeMenuView()
}

@PreviewLightDark
@Composable
private fun ExchangeScreenPreview() {
//    ExchangeScreenView(
//        mockCurrencyUIList()
//    )
}
