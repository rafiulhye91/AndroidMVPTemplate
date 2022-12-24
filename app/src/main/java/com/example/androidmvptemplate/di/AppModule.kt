package com.example.androidmvptemplate.di

import com.example.androidmvptemplate.data.domain.remote.ApiServices
import com.example.androidmvptemplate.data.domain.remote.ApiServices.Companion.BASE_URL
import com.example.androidmvptemplate.data.domain.remote.ApiServices.Companion.TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiServices(): ApiServices {
        val client = OkHttpClient.Builder()
            .callTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiServices::class.java)
    }
}