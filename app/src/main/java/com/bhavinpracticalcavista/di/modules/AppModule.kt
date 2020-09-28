package com.bhavinpracticalcavista.di.modules

import android.app.Application
import android.content.Context
import com.bhavinpracticalcavista.BuildConfig
import com.bhavinpracticalcavista.CavistaApp
import com.bhavinpracticalcavista.api.ConnectivityInterceptor
import com.bhavinpracticalcavista.api.HeaderInterceptor
import com.bhavinpracticalcavista.api.RetrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: CavistaApp): Context {
        return application.applicationContext
    }


    @Provides
    @Singleton
    fun provideHttpCache(context: Context): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitService(retroFit: Retrofit): RetrofitService {
        return retroFit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun getGson(): Gson {
        return GsonBuilder()
            .serializeNulls().setPrettyPrinting().setLenient()
            .create()
    }


    @Singleton
    @Provides
    fun getOkHttpCleint(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        connectivityInterceptor: ConnectivityInterceptor,
        headerInterceptor: HeaderInterceptor,
        cache: Cache
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.connectTimeout(2, TimeUnit.MINUTES)
        httpClient.readTimeout(2, TimeUnit.MINUTES)
        httpClient.writeTimeout(2, TimeUnit.MINUTES)
        httpClient.retryOnConnectionFailure(true)
        httpClient.addInterceptor(connectivityInterceptor)
        httpClient.addInterceptor(headerInterceptor)
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLoggingInterceptor)
        }
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


}