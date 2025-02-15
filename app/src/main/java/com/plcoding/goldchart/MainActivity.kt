package com.plcoding.goldchart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plcoding.goldchart.app.Routes
import com.plcoding.goldchart.presentation.MainContent
import com.plcoding.goldchart.presentation.component.BottomNavUtil
import com.plcoding.goldchart.presentation.theme.GoldChartTheme

class MainActivity : ComponentActivity(), AnalyticLogger by AnalyticLoggerImpl() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLifecycleOwner(this)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GoldChartTheme {
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
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unSelectedIcon,
                        contentDescription = item.title,
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Default,
                        modifier = Modifier.padding(vertical = 0.dp)
                    )
                }
            )
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