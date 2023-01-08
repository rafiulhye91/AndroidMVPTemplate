package com.example.androidmvptemplate.service.sample

import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.domain.model.SampleDomainData
import com.example.androidmvptemplate.domain.sample.ISampleDomain
import javax.inject.Inject

class SampleService @Inject constructor(private val domain: ISampleDomain) : ISampleService {
    override suspend fun getSampleData(): Resource<SampleDomainData?> {
        return domain.getSampleData()
    }

    override suspend fun getAllSampleData(): Resource<List<SampleDomainData?>> {
        return domain.getAllSampleData()
    }

}