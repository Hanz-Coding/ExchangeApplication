package com.plcoding.goldchart

import android.app.Application
import com.plcoding.goldchart.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AssetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AssetApplication)
            androidLogger()
            modules(appModule)
        }
    }
}