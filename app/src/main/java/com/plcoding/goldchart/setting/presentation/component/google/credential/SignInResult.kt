package com.plcoding.goldchart.setting.presentation.component.google.credential

sealed interface SignInResult {
    data class Success(val userName: String) : SignInResult
    data object Cancelled : SignInResult
    data object Failure : SignInResult
    data object NoCredential : SignInResult
}