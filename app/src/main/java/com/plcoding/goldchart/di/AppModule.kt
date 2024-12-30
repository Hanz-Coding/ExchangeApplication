package com.plcoding.goldchart.di

import android.app.Application
import androidx.room.Room
import com.plcoding.goldchart.core.data.networking.Constant
import com.plcoding.goldchart.core.data.networking.HttpClientFactory
import com.plcoding.goldchart.core.data.database.AssetDatabase
import com.plcoding.goldchart.exchange.data.network.RemoteBIDVCurrencyDatasource
import com.plcoding.goldchart.exchange.data.network.RemoteVCBCurrencyDatasource
import com.plcoding.goldchart.exchange.data.repository.CurrencyRepositoryImpl
import com.plcoding.goldchart.exchange.data.network.api.BIDVCurrencyApi
import com.plcoding.goldchart.exchange.data.network.api.VCBCurrencyApi
import com.plcoding.goldchart.exchange.domain.CurrencyRepository
import com.plcoding.goldchart.exchange.domain.datasource.BIDVCurrencyDataSource
import com.plcoding.goldchart.exchange.domain.datasource.VCBCurrencyDataSource
import com.plcoding.goldchart.exchange.presentation.ExchangeViewModel
import com.plcoding.goldchart.gold.data.network.RemotePNJAssetsDataSource
import com.plcoding.goldchart.gold.data.network.RemoteSJCAssetsDataSource
import com.plcoding.goldchart.gold.data.network.retrofit.PNJAssetApi
import com.plcoding.goldchart.gold.data.network.retrofit.SJCAssetApi
import com.plcoding.goldchart.gold.data.repository.AssetsRepositoryImpl
import com.plcoding.goldchart.gold.domain.datasource.PNJAssetsDataSource
import com.plcoding.goldchart.gold.domain.datasource.SJCAssetsDataSource
import com.plcoding.goldchart.gold.domain.repository.AssetsRepository
import com.plcoding.goldchart.gold.presentation.gold_price.GoldPriceViewModel
import com.plcoding.goldchart.gold.presentation.home.AssetsViewModel
import io.ktor.client.engine.cio.CIO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // tạo singleton cho đối tượng phức tạp, không sử dụng constructor có sẵn
    single { HttpClientFactory.create(CIO.create()) }

    // single of : tạo single cho đối tượng thông qua constructor mặc định
    // bind : gán interface cho object, Koin cung cấp đối tượng RemoteCoinDataSource
    // khi trong hàm yêu cầu interface CoinDataSource
    single { RemoteSJCAssetsDataSource(get()) }.bind<SJCAssetsDataSource>()
    single { RemotePNJAssetsDataSource(get()) }.bind<PNJAssetsDataSource>()
    single { AssetsRepositoryImpl(get(), get(), get()) }.bind<AssetsRepository>()

    single { RemoteVCBCurrencyDatasource(get()) }.bind<VCBCurrencyDataSource>()
    single { RemoteBIDVCurrencyDatasource(get()) }.bind<BIDVCurrencyDataSource>()
    single { CurrencyRepositoryImpl(get(), get(), get()) }.bind<CurrencyRepository>()

    single { get<Retrofit>(named("sjcRetrofit")).create(SJCAssetApi::class.java) }
    single(named("sjcRetrofit")) {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_SJC)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>(named("pnjRetrofit")).create(PNJAssetApi::class.java) }
    single(named("pnjRetrofit")) {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_PNJ)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

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

    single {
        Room.databaseBuilder(
            get<Application>(),
            AssetDatabase::class.java, AssetDatabase.DB_NAME
        ).build()
    }.bind<AssetDatabase>()

    single { get<AssetDatabase>().currencyDAO }
    viewModelOf(::AssetsViewModel)
    viewModelOf(::GoldPriceViewModel)
    viewModelOf(::ExchangeViewModel)
}