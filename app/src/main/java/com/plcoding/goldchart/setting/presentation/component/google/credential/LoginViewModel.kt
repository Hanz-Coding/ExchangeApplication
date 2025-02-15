package com.plcoding.goldchart.setting.presentation.component.google.credential

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.plcoding.goldchart.setting.presentation.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    var state = MutableStateFlow(LoginState())
    val _state = state.asStateFlow()

    init {
        println("hanz init ViewModel: $state")
    }

    override fun onCleared() {
        println("hanz onCleared ViewModel: $state")
        super.onCleared()
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnPasswordChange -> {
                state.update { it.copy(password = action.password) }
            }

            is LoginAction.OnSignUp -> {
                when (action.result) {
                    SignUpResult.Cancelled -> {
                        state.update { it.copy(errorMessage = "Sign up cancelled") }
                    }

                    SignUpResult.Failure -> {
                        state.update { it.copy(errorMessage = "Sign up failed") }
                    }

                    is SignUpResult.Success -> {
                        println("hanz ViewModelSuccess: $state")
                        state.update { it.copy(loggedInUser = action.result.userName) }.also {
                            println("hanz ViewModel: $state")
                        }
                    }
                }
            }

            is LoginAction.OnSignIn -> {
                when (action.result) {
                    SignInResult.Cancelled -> {
                        state.update { it.copy(errorMessage = "Sign in cancelled") }
                    }

                    SignInResult.Failure -> {
                        state.update { it.copy(errorMessage = "Sign in failed") }
                    }

                    is SignInResult.Success -> {
                        println("hanz ViewModel  Success: $state")
                        state.update { it.copy(loggedInUser = action.result.userName) }.also {
                            println("hanz ViewModel: $state name ${action.result.userName}")
                        }
                    }

                    SignInResult.NoCredential -> {
                        state.update { it.copy(errorMessage = "No credential was setup") }
                    }
                }
            }

            LoginAction.OnToggleIsRegister -> {
                state.update { it.copy(isRegister = !it.isRegister) }
            }

            is LoginAction.OnUsernameChange -> {
                state.update { it.copy(username = action.username) }
            }
        }
    }
}

