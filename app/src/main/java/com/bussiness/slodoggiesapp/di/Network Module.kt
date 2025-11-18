package com.bussiness.slodoggiesapp.di

import android.content.Context
import com.bussiness.slodoggiesapp.data.remote.ApiService
import com.bussiness.slodoggiesapp.data.remote.Repository
import com.bussiness.slodoggiesapp.data.remote.RepositoryImpl
import com.bussiness.slodoggiesapp.network.NetworkInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        val cache = Cache(context.cacheDir, cacheSize)

        val logging = HttpLoggingInterceptor { message ->
            android.util.Log.d("API_LOGS", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(networkInterceptor)
            .addInterceptor(logging)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://slodoggies.tgastaging.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepositoryModule {

        @Binds
        @Singleton
        abstract fun bindRepository(
            impl: RepositoryImpl
        ): Repository
    }
}