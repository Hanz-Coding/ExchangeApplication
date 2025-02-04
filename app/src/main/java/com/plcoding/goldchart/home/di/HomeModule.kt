package com.plcoding.goldchart.home.di

import com.plcoding.goldchart.domain.Repository
import com.plcoding.goldchart.home.data.Constant
import com.plcoding.goldchart.home.data.network.api.NewsApi
import com.plcoding.goldchart.home.data.network.datasource.NewsDataSourceImpl
import com.plcoding.goldchart.home.data.network.repository.NewsRepositoryImpl
import com.plcoding.goldchart.home.domain.NewsDataSource
import com.plcoding.goldchart.home.domain.NewsRepository
import com.plcoding.goldchart.home.presentation.viewmodel.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val homeModule = module {
    single { NewsRepositoryImpl(get(), get()) }.bind<NewsRepository>()
    single { NewsDataSourceImpl(get()) }.bind<NewsDataSource>()

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Để log chi tiết

        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    single { get<Retrofit>(named("cafef")).create(NewsApi::class.java) }
    single(named("cafef")) {
        Retrofit.Builder().baseUrl(Constant.BASE_URL_CAFEF).client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single {
        HomeViewModel(
            get<Repository>(named("assetRepo")),
            get<Repository>(named("exchangeRepo")), get()
        )
    }
}