package com.plcoding.goldchart.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.plcoding.goldchart.app.Routes
import com.plcoding.goldchart.exchange.presentation.ExchangeScreenRoot
import com.plcoding.goldchart.forum.presentation.ForumScreenRoot
import com.plcoding.goldchart.gold.presentation.GoldPriceScreenRoot
import com.plcoding.goldchart.home.presentation.components.HomeScreenRoot
import com.plcoding.goldchart.setting.presentation.SettingScreenRoot
import com.plcoding.goldchart.setting.presentation.component.google.credential.LoginScreen
import com.plcoding.goldchart.setting.presentation.component.google.credential.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainContent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.AssetsGraph
    )
    {
        navigation<Routes.AssetsGraph>(
            startDestination = Routes.HomeScreen
        ) {
            composable<Routes.HomeScreen> {
                HomeScreenRoot()
            }

            composable<Routes.GoldPriceScreen> {
                GoldPriceScreenRoot()
            }

            composable<Routes.ExchangeScreen> {
                ExchangeScreenRoot()
            }
            composable<Routes.ForumScreen> {
                ForumScreenRoot()
            }
            composable<Routes.SettingScreen> {
                SettingScreenRoot(navController = navController)
            }

            composable<Routes.LoginScreen> {
                val settingViewModel = koinViewModel<LoginViewModel>()
                LoginScreen(
                    settingViewModel.state.value,
                    onAction = settingViewModel::onAction,
                    onLogin = {
                        navController.navigate(Routes.SettingScreen) {
                            popUpTo(Routes.LoginScreen) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}