package com.plcoding.goldchart.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AssetScreen(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column (modifier = Modifier.fillMaxWidth()){
        Text(text = title)
    }
}