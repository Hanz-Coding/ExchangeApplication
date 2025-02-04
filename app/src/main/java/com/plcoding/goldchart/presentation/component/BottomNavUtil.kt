package com.plcoding.goldchart.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PriceChange
import androidx.compose.material.icons.outlined.PriceCheck
import androidx.compose.material.icons.outlined.Settings

object BottomNavUtil {
    val bottomItems = listOf(
        BottomNavigationItem(
            title = "Trang chủ",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            hasNew = false,
        ), BottomNavigationItem(
            title = "Giá vàng",
            selectedIcon = Icons.Filled.PriceChange,
            unSelectedIcon = Icons.Outlined.PriceChange,
            hasNew = false,
        ), BottomNavigationItem(
            title = "Tỉ giá",
            selectedIcon = Icons.Filled.PriceCheck,
            unSelectedIcon = Icons.Outlined.PriceCheck,
            hasNew = false,
        ), BottomNavigationItem(
            title = "Cộng đồng",
            selectedIcon = Icons.Filled.Forum,
            unSelectedIcon = Icons.Outlined.Forum,
            hasNew = false,
        ), BottomNavigationItem(
            title = "Cài đặt",
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            hasNew = false,
        )
    )
}