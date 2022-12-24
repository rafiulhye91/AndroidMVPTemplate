package com.example.androidmvptemplate.data.remote.model

import com.example.androidmvptemplate.domain.model.SampleDomainData
import com.google.gson.annotations.SerializedName

data class SampleDTO(
    @SerializedName("name")
    val name: String?
) {
    fun toSampleDomainData(): SampleDomainData {
        return SampleDomainData(
            name = this.name
        )
    }
}
