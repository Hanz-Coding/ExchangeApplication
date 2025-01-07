package com.plcoding.goldchart.gold.di

import com.plcoding.goldchart.gold.data.network.RemotePNJAssetsDataSource
import com.plcoding.goldchart.gold.data.network.RemoteSJCAssetsDataSource
import com.plcoding.goldchart.gold.data.network.api.PNJAssetApi
import com.plcoding.goldchart.gold.data.network.api.SJCAssetApi
import com.plcoding.goldchart.gold.data.repository.AssetsRepositoryImpl
import com.plcoding.goldchart.gold.data.utils.Constant
import com.plcoding.goldchart.gold.domain.datasource.AssetsDataSource
import com.plcoding.goldchart.gold.domain.repository.AssetsRepository
import com.plcoding.goldchart.gold.presentation.GoldPriceViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val goldModule = module {
    single { RemoteSJCAssetsDataSource(get()) }.bind<AssetsDataSource>()
    single { RemotePNJAssetsDataSource(get()) }.bind<AssetsDataSource>()
    single { AssetsRepositoryImpl(get(), get(), get()) }.bind<AssetsRepository>()

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Để log chi tiết

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single { get<Retrofit>(named("sjcRetrofit")).create(SJCAssetApi::class.java) }
    single(named("sjcRetrofit")) {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_SJC)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>(named("pnjRetrofit")).create(PNJAssetApi::class.java) }
    single(named("pnjRetrofit")) {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_PNJ)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    viewModelOf(::GoldPriceViewModel)
}