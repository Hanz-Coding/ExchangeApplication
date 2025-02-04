package com.plcoding.goldchart.exchange.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.plcoding.goldchart.exchange.domain.CompanyName
import com.plcoding.goldchart.exchange.presentation.components.BIDVExchangeScreenRoot
import com.plcoding.goldchart.exchange.presentation.components.VCBExchangeScreenRoot
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeScreenRoot(
    viewModel: ExchangeViewModel = koinViewModel<ExchangeViewModel>(),
) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
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
            tabContents(index, viewModel::onPullToRefreshTrigger)
        }
    }
}

val tabContents: @Composable (Int, (String) -> Unit) -> Unit =
    { index, onRefresh ->
        when (index) {
            0 -> {
                VCBExchangeScreenRoot(
                    onRefreshTrigger = { onRefresh(CompanyName.VCB) }
                )
            }

            1 -> {
                BIDVExchangeScreenRoot()
            }
        }
    }

@PreviewLightDark
@Composable
private fun ExchangeScreenPreview() {
}
