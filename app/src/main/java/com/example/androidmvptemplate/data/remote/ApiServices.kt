package com.example.androidmvptemplate.data.remote

import com.example.androidmvptemplate.data.remote.model.SampleDTO
import retrofit2.http.GET

interface ApiServices {
    companion object {
        const val BASE_URL = "sample.url.com"
        const val TIMEOUT: Long = 5
    }

    @GET("sample_route/")
    suspend fun getSampleData(): SampleDTO
}