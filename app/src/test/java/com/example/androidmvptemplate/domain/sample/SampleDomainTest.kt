package com.example.androidmvptemplate.domain.sample

import com.example.androidmvptemplate.data.local.DaoServices
import com.example.androidmvptemplate.data.remote.ApiServices
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SampleDomainTest {
    private val apiServices: ApiServices = mockk(relaxed = true)
    private val daoServices: DaoServices = mockk(relaxed = true)
    private val sampleDomain: SampleDomain = SampleDomain(apiServices, daoServices)

    @Test
    fun `When getSampleData is called from network, Then ApiServices calls the getSampleData`() =
        runTest {
            sampleDomain.getSampleData()
            coVerify { apiServices.getSampleData() }
        }

    @Test
    fun `When getAllSampleData is called from db, the DaoServices calls the getAllSampleData`() =
        runTest {
            sampleDomain.getAllSampleData()
            coVerify { daoServices.getAllSampleDbData() }
        }

}