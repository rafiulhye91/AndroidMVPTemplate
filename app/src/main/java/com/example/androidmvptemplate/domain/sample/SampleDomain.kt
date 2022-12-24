package com.example.androidmvptemplate.domain.sample

import com.example.androidmvptemplate.data.local.DaoServices
import com.example.androidmvptemplate.data.remote.ApiServices
import javax.inject.Inject

class SampleDomain @Inject constructor(
    private val apiServices: ApiServices,
    daoServices: DaoServices
) : ISampleDomain {

}