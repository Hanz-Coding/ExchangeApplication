package com.plcoding.goldchart.exchange.di

import com.plcoding.goldchart.domain.Repository
import com.plcoding.goldchart.domain.datasource.CurrencyDataSource
import com.plcoding.goldchart.exchange.data.Constant
import com.plcoding.goldchart.exchange.data.network.RemoteBIDVCurrencyDatasource
import com.plcoding.goldchart.exchange.data.network.RemoteVCBCurrencyDatasource
import com.plcoding.goldchart.exchange.data.network.api.BIDVCurrencyApi
import com.plcoding.goldchart.exchange.data.network.api.VCBCurrencyApi
import com.plcoding.goldchart.exchange.data.repository.ExchangeRepositoryImpl
import com.plcoding.goldchart.exchange.presentation.ExchangeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val exchangeModule = module {
    single { RemoteVCBCurrencyDatasource(get()) }.bind<CurrencyDataSource>()
    single { RemoteBIDVCurrencyDatasource(get()) }.bind<CurrencyDataSource>()
    single (named("exchangeRepo")){ ExchangeRepositoryImpl(get(), get(), get()) }.bind<Repository>()

    single { get<Retrofit>(named("bidvRetrofit")).create(BIDVCurrencyApi::class.java) }
    single(named("bidvRetrofit")) {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_BIDV)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>(named("vcbRetrofit")).create(VCBCurrencyApi::class.java) }
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Để log chi tiết

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    single(named("vcbRetrofit")) {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_VCB)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { ExchangeViewModel(get<Repository>(named("exchangeRepo"))) }
//    viewModelOf(::ExchangeViewModel)
}