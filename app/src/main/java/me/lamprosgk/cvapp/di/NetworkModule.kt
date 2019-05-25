package me.lamprosgk.cvapp.di

import dagger.Module
import dagger.Provides
import me.lamprosgk.cvapp.BASE_URL
import me.lamprosgk.cvapp.BuildConfig
import me.lamprosgk.cvapp.data.remote.CvService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val NAME_BASE_URL = "BASE_URL"
    }

    @Provides
    @Named(NAME_BASE_URL)
    fun provideBaseUrlString() = BASE_URL

    @Provides
    @Singleton
    fun provideLoggingCapableHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, @Named(NAME_BASE_URL) baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideCvService(retrofit: Retrofit): CvService = retrofit.create(CvService::class.java)

}