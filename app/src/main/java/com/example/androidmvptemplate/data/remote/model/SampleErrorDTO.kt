package com.example.androidmvptemplate.data.remote.model


import com.google.gson.annotations.SerializedName

data class SampleErrorDTO(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)