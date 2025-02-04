package com.plcoding.goldchart.gold.di

import com.plcoding.goldchart.domain.Repository
import com.plcoding.goldchart.domain.datasource.CurrencyDataSource
import com.plcoding.goldchart.gold.data.network.RemotePNJAssetsDataSource
import com.plcoding.goldchart.gold.data.network.RemoteSJCAssetsDataSource
import com.plcoding.goldchart.gold.data.network.api.PNJAssetApi
import com.plcoding.goldchart.gold.data.network.api.SJCAssetApi
import com.plcoding.goldchart.gold.data.repository.GoldRepositoryImpl
import com.plcoding.goldchart.gold.data.utils.Constant
import com.plcoding.goldchart.gold.presentation.viewmodel.GoldPriceViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val goldModule = module {
    single { RemoteSJCAssetsDataSource(get()) }.bind<CurrencyDataSource>()
    single { RemotePNJAssetsDataSource(get()) }.bind<CurrencyDataSource>()
    single(named("assetRepo")) { GoldRepositoryImpl(get(), get(), get()) }.bind<Repository>()

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
    single { GoldPriceViewModel(get<Repository>(named("assetRepo"))) }
}