package com.plcoding.goldchart.gold.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.goldchart.gold.presentation.tab_view.SJCPriceViewRoot
import com.plcoding.goldchart.gold.presentation.viewmodel.GoldPriceViewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun GoldPriceScreenRoot(
) {
    GoldPriceScreen()
}

@Composable
fun GoldPriceScreen(
) {
    Column(
        modifier = Modifier.padding(bottom = 90.dp)
    ) {
        val viewModel = koinViewModel<GoldPriceViewModel>()
//        val tabItems = listOf("SJC", "PNJ", "DOJI", "Bảo Tín Minh Châu", "Mi Hồng", "Vàng Thế Giới")
        val tabItems = listOf("SJC", "PNJ")
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

        Column(modifier = Modifier.fillMaxSize()) {
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
                tabContents(index, viewModel.getTabState(tabItems[index]).also {
                    println("hanz tabItems[index] ${tabItems[index]}")
                })
            }
        }
    }
}

val tabContents: @Composable (Int, StateFlow<CategoryState>) -> Unit = { index, categoryState ->
    when (index) {
        0 -> {
            SJCPriceViewRoot(categoryState)
        }

        1 -> {
            SJCPriceViewRoot(categoryState)
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = index.toString())
            }
        }
    }
}