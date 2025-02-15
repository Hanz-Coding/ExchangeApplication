package com.plcoding.goldchart.setting.presentation.component.google.credential

sealed interface SignUpResult {
    data class Success(val userName: String) : SignUpResult
    data object Cancelled:SignUpResult
    data object Failure : SignUpResult
}