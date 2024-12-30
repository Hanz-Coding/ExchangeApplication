package com.plcoding.goldchart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.plcoding.goldchart.app.Routes
import com.plcoding.goldchart.exchange.presentation.ExchangeScreenRoot
import com.plcoding.goldchart.forum.presentation.ForumScreenRoot
import com.plcoding.goldchart.gold.presentation.gold_price.GoldPriceScreenRoot
import com.plcoding.goldchart.gold.presentation.home.AssetsViewModel
import com.plcoding.goldchart.gold.presentation.home.HomeScreenRoot
import com.plcoding.goldchart.gold.presentation.home.components.AssetScreen
import com.plcoding.goldchart.setting.presentation.SettingScreenRoot
import com.plcoding.goldchart.ui.components.BottomNavUtil
import com.plcoding.goldchart.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity(), AnalyticLogger by AnalyticLoggerImpl() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleOwner(this)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                    bottomBar = {
                        NavigationBarCustom(navController)
                    }
                ) {
                    MainContent(navController)
                }
            }
        }
    }
}

@Composable
fun NavigationBarCustom(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        BottomNavUtil.bottomItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    when (index) {
                        0 -> navController.navigate(Routes.HomeScreen) {
                            // Pop up tất cả các tab sau tab hiện tại khi chuyển đổi
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }

                        1 -> navController.navigate(Routes.GoldPriceScreen)
                        2 -> navController.navigate(Routes.ExchangeScreen)
                        3 -> navController.navigate(Routes.ForumScreen)
                        4 -> navController.navigate(Routes.SettingScreen)
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {

                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unSelectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}

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
                val viewModel = koinViewModel<AssetsViewModel>()
                HomeScreenRoot(
                    viewModel = viewModel,
                )
            }

            composable<Routes.AssetDetail> { entry ->
                val args = entry.toRoute<Routes.AssetDetail>()
                AssetScreen(args.title)
            }

            composable<Routes.GoldPriceScreen> {
//                val viewModel = koinViewModel<GoldPriceViewModel>()
//                GoldPriceScreenRoot(viewModel)
                GoldPriceScreenRoot()
            }

            composable<Routes.ExchangeScreen> {
                ExchangeScreenRoot()
            }
            composable<Routes.ForumScreen> {
                ForumScreenRoot()
            }
            composable<Routes.SettingScreen> {
                SettingScreenRoot()
            }
        }
    }
}

interface AnalyticLogger {
    fun registerLifecycleOwner(owner: LifecycleOwner)
}

class AnalyticLoggerImpl : AnalyticLogger, LifecycleEventObserver {
    override fun registerLifecycleOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                println("application is open")
            }

            Lifecycle.Event.ON_PAUSE -> {
                println("application is close")
            }

            else -> {}
        }
    }
}