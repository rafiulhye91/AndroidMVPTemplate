package com.example.androidmvptemplate.data.domain.remote.model

import com.google.gson.annotations.SerializedName

data class SampleDTO(
    @SerializedName("name")
    val name: String?
)
