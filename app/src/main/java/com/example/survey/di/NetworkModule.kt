package com.example.survey.di

import com.example.survey.common.definitions.Definitions
import com.example.survey.network.NetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@InstallIn(ViewModelComponent::class)
@Module
object NetworkModule {

    @Provides
    fun providesService(okHttpClient: OkHttpClient): NetworkApi =
        Retrofit.Builder()
            .baseUrl(Definitions.DOMAIN)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkApi::class.java)

    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()
}