package com.plcoding.goldchart.di

import android.app.Application
import androidx.room.Room
import com.plcoding.goldchart.core.data.database.AssetDatabase
import com.plcoding.goldchart.core.data.networking.HttpClientFactory
import com.plcoding.goldchart.home.presentation.AssetsViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    // tạo singleton cho đối tượng phức tạp, không sử dụng constructor có sẵn
    single { HttpClientFactory.create(CIO.create()) }

    // single of : tạo single cho đối tượng thông qua constructor mặc định
    // bind : gán interface cho object, Koin cung cấp đối tượng RemoteCoinDataSource
    // khi trong hàm yêu cầu interface CoinDataSource

    single {
        Room.databaseBuilder(
            get<Application>(),
            AssetDatabase::class.java, AssetDatabase.DB_NAME
        ).build()
    }.bind<AssetDatabase>()

    single { get<AssetDatabase>().currencyDAO }
    viewModelOf(::AssetsViewModel)
}