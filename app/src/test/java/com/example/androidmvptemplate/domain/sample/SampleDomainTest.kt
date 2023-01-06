package com.example.androidmvptemplate.domain.sample

import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.data.local.DaoServices
import com.example.androidmvptemplate.data.remote.ApiServices
import com.example.androidmvptemplate.data.remote.model.SampleDTO
import com.example.androidmvptemplate.domain.generateMockErrorResponseBody
import com.example.androidmvptemplate.domain.model.SampleDomainData
import com.example.androidmvptemplate.domain.util.NotFoundException
import com.example.androidmvptemplate.domain.util.UnauthorizedException
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class SampleDomainTest {
    private val apiServices: ApiServices = mockk(relaxed = true)
    private val daoServices: DaoServices = mockk(relaxed = true)
    private val sampleDomain: SampleDomain = SampleDomain(apiServices, daoServices)

    @Test
    fun `When getSampleData is called from network, Then ApiServices calls the getSampleData`() =
        runTest {
            coEvery { apiServices.getSampleData() } returns Response.success(null)
            sampleDomain.getSampleData()
            coVerify { apiServices.getSampleData() }
        }

    @Test
    fun `When getAllSampleData is called from db, the DaoServices calls the getAllSampleData`() =
        runTest {
            sampleDomain.getAllSampleData()
            coVerify { daoServices.getAllSampleDbData() }
        }

    @Test
    fun `Given API call returns a UnauthorizedException exception, When getEvents is called, return the unauthorized error`() =
        runTest {
            coEvery { apiServices.getSampleData() }.throws(
                UnauthorizedException(
                    generateMockErrorResponseBody()
                )
            )
            val result = sampleDomain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = "User is unauthorized!")
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a NotFoundException exception, When getEvents is called, return the page not found error`() =
        runTest {
            coEvery { apiServices.getSampleData() }.throws(
                NotFoundException(
                    generateMockErrorResponseBody()
                )
            )
            val result = sampleDomain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = "Page Not Found!")
            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `Given API call returns a HTTPException exception, When getEvents is called, return the network error`() =
        runTest {
            val response = Response.error<List<SampleDTO>>(500, generateMockErrorResponseBody())
            coEvery { apiServices.getSampleData() }.throws(HttpException(response))
            val result = sampleDomain.getSampleData()
            val expected = Resource.Error<List<SampleDomainData>>(error = "Network Error!")
            assertThat(result.error).isEqualTo(expected.error)
        }
}