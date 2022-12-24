package com.example.androidmvptemplate.data.remote.model

import com.google.gson.annotations.SerializedName

data class SampleDTO(
    @SerializedName("name")
    val name: String?
)
