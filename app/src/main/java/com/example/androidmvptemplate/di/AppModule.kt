package com.example.androidmvptemplate.di

import android.app.Activity
import android.app.Application
import androidx.room.Room
import com.example.androidmvptemplate.BuildConfig
import com.example.androidmvptemplate.data.local.AppDatabase
import com.example.androidmvptemplate.data.local.AppDatabase.Companion.DATABASE_NAME
import com.example.androidmvptemplate.data.local.DaoServices
import com.example.androidmvptemplate.data.remote.ApiServices
import com.example.androidmvptemplate.data.remote.ApiServices.Companion.BASE_URL
import com.example.androidmvptemplate.data.remote.ApiServices.Companion.TIMEOUT
import com.example.androidmvptemplate.data.remote.AuthInterceptor
import com.example.androidmvptemplate.domain.sample.ISampleDomain
import com.example.androidmvptemplate.domain.sample.SampleDomain
import com.example.androidmvptemplate.mock.MockApiServices
import com.example.androidmvptemplate.service.sample.ISampleService
import com.example.androidmvptemplate.service.sample.SampleService
import com.example.androidmvptemplate.view.main.IMainView
import com.example.androidmvptemplate.view.sample.ISampleView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 *
 * AppModule is a Dagger2 module that provides objects and dependencies
 * that are used throughout the application. These dependencies are scoped
 * to the entire application's lifecycle.
 *
 * @property apiKey the API key used for authentication with the API
 * @property authInterceptor an interceptor that adds the API key to requests
 * @property loggingInterceptor an interceptor that logs requests and responses
 * @property apiServices the API services used to make network requests
 * @property useMockApi a boolean value indicating whether to use mock API services or not
 * @property database the app's database instance
 * @property daoServices the Data Access Object services for the app's database
 */


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    //TODO: Find a way to securely save the API token and provide it from here
    fun provideApiKey(): String = "Token"

    @Provides
    fun provideAuthInterceptor(apiKey: String): AuthInterceptor = AuthInterceptor(apiKey)

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideApiServices(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        app: Application,
        @Named("useMockApi") useMockApi: Boolean
    ): ApiServices {
        return if (useMockApi) {
            MockApiServices(app)
        } else {
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .callTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiServices::class.java)
        }
    }

    @Provides
    @Named("useMockApi")
    fun provideUseMockApi(): Boolean = BuildConfig.USE_MOCK_API

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
    fun provideSampleService(domain: ISampleDomain): ISampleService {
        return SampleService(domain)
    }
}

/**
 *
 * ViewModule is a Dagger2 module that provides objects and dependencies
 * that are scoped to the lifecycle of an Activity.
 */
@InstallIn(ActivityComponent::class)
@Module
object ViewModule {

    @Provides
    fun provideIMainView(activity: Activity): IMainView {
        return activity as IMainView
    }

    @Provides
    fun provideISampleView(activity: Activity): ISampleView {
        return activity as ISampleView
    }

}