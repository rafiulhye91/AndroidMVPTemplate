package com.example.androidmvptemplate.domain.sample

import com.example.androidmvptemplate.base.BaseDomain
import com.example.androidmvptemplate.data.ItemWrapper
import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.data.local.DaoServices
import com.example.androidmvptemplate.data.remote.ApiServices
import com.example.androidmvptemplate.domain.model.SampleDomainData
import javax.inject.Inject

class SampleDomain @Inject constructor(
    private val apiServices: ApiServices,
    private val daoServices: DaoServices
) : BaseDomain(), ISampleDomain {

    override suspend fun getSampleData(): Resource<SampleDomainData?> {
        return handleApiResponse {
            val response = apiServices.getSampleData()
            ItemWrapper(response.body()?.toSampleDomainData(), response)
        }
    }

    override suspend fun getAllSampleData(): Resource<List<SampleDomainData?>> {
        return handleDbResponse {
            daoServices.getAllSampleDbData().map { it.toSampleDomainData() }
        }
    }

}