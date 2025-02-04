@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.plcoding.goldchart.home.presentation.components.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.goldchart.home.presentation.model.NewsUI

@ExperimentalMaterial3Api
@Composable
fun NewsBottomSheet(
    isBottomSheetVisible: Boolean,
    sheetState: SheetState,
    url: String?,
    onDismiss: () -> Unit,
) {
    println("hanz ModalBottomSheet $isBottomSheetVisible")
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 80.dp),
            sheetState = sheetState,
            onDismissRequest = onDismiss,
            dragHandle = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                println("hanz ModalBottomSheet $url")
                url?.let {
                    WebBrowser(it, onDismiss)
                }
            }
        }
    }
}