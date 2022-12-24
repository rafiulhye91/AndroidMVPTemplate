package com.example.androidmvptemplate.di

import android.app.Activity
import android.app.Application
import androidx.room.Room
import com.example.androidmvptemplate.data.local.AppDatabase
import com.example.androidmvptemplate.data.local.AppDatabase.Companion.DATABASE_NAME
import com.example.androidmvptemplate.data.local.DaoServices
import com.example.androidmvptemplate.data.remote.ApiServices
import com.example.androidmvptemplate.data.remote.ApiServices.Companion.BASE_URL
import com.example.androidmvptemplate.data.remote.ApiServices.Companion.TIMEOUT
import com.example.androidmvptemplate.domain.sample.ISampleDomain
import com.example.androidmvptemplate.domain.sample.SampleDomain
import com.example.androidmvptemplate.service.SampleService
import com.example.androidmvptemplate.view.main.IMainView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
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

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideDaoServices(database: AppDatabase): DaoServices {
        return database.getDaoServices()
    }

    @Provides
    @Singleton
    fun provideSampleDomain(apiServices: ApiServices, daoServices: DaoServices): ISampleDomain {
        return SampleDomain(apiServices, daoServices)
    }

    @Provides
    @Singleton
    fun provideSampleService(domain: ISampleDomain): SampleService {
        return SampleService(domain)
    }
}

@InstallIn(ActivityComponent::class)
@Module
object ViewModule {

    @Provides
    fun provideIMainView(activity: Activity): IMainView {
        return activity as IMainView
    }
}