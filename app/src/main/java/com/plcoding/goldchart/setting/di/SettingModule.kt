package com.plcoding.goldchart.setting.di

import com.plcoding.goldchart.setting.presentation.component.google.credential.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingModule = module {
    viewModelOf(::LoginViewModel)
}