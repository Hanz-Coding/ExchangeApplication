package com.plcoding.goldchart

import android.app.Application
import com.plcoding.goldchart.di.appModule
import com.plcoding.goldchart.exchange.di.exchangeModule
import com.plcoding.goldchart.gold.di.goldModule
import com.plcoding.goldchart.home.di.homeModule
import com.plcoding.goldchart.setting.di.settingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AssetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AssetApplication)
            androidLogger()
            modules(appModule, exchangeModule, goldModule, homeModule, settingModule)
        }
    }
}