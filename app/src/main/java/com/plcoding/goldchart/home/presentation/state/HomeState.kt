package com.plcoding.goldchart.home.presentation.state

data class HomeState(
    val isLoading: Boolean = false,
    val listItem: MutableMap<Int, Any> = mutableMapOf(),
)
