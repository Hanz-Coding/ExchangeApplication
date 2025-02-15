package com.plcoding.goldchart.setting.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.plcoding.goldchart.app.Routes
import com.plcoding.goldchart.setting.presentation.component.UserInfoView
import com.plcoding.goldchart.setting.presentation.component.google.credential.AccountManager
import com.plcoding.goldchart.setting.presentation.component.google.credential.LoginViewModel
import com.plcoding.goldchart.setting.presentation.state.LoginState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingScreenRoot(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val viewModel = koinViewModel<LoginViewModel>()
    val context = LocalContext.current
    val accountManager = remember {
        AccountManager(context as ComponentActivity)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingScreen(state,
        onNavigateLogin = { navController.navigate(Routes.LoginScreen) },
        onSignOut = {
            scope.launch {
                accountManager.signOut()
            }
        })
}

@Preview
@Composable
private fun SettingScreen(
    state: LoginState,
    onNavigateLogin: () -> Unit,
    onSignOut: () -> Unit,
) {
    println("hanz SettingScreen: $state")
    if (state.loggedInUser != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserInfoView(state.username)
            Button(onClick = {
                onSignOut()
            }) {
                Text(text = "Đăng xuất")
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                onNavigateLogin()
            }) {
                Text(text = "Đăng nhập")
            }
        }
    }
}