package com.plcoding.goldchart.app

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object AssetsGraph :Routes

    @Serializable
    data object HomeScreen : Routes

    @Serializable
    data object GoldPriceScreen : Routes

    @Serializable
    data object ExchangeScreen : Routes

    @Serializable
    data object ForumScreen : Routes

    @Serializable
    data object SettingScreen : Routes

    @Serializable
    data class AssetDetail(val title: String) : Routes
}