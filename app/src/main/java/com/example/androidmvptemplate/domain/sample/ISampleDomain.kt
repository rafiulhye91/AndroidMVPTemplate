package com.example.androidmvptemplate.domain.sample

import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.domain.model.SampleDomainData

interface ISampleDomain {
    suspend fun getSampleData(): Resource<SampleDomainData?>
    suspend fun getAllSampleData(): Resource<List<SampleDomainData?>>
}