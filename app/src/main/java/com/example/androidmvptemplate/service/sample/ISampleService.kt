package com.example.androidmvptemplate.service.sample

import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.domain.model.SampleDomainData

interface ISampleService {
    suspend fun getSampleData(): Resource<SampleDomainData?>
    suspend fun getAllSampleData(): Resource<List<SampleDomainData?>>
}