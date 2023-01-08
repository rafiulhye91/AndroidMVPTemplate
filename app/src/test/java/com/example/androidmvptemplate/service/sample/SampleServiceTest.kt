package com.example.androidmvptemplate.service.sample

import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.domain.model.SampleDomainData
import com.example.androidmvptemplate.domain.sample.ISampleDomain
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SampleServiceTest {
    private val domain: ISampleDomain = mockk(relaxed = true)
    private val service: SampleService = SampleService(domain)

    @Test
    fun `When getSampleData is called, Then domain also calls the getSampleData`() = runTest {
        service.getSampleData()
        coVerify { domain.getSampleData() }
    }

    @Test
    fun `When getAllEvents is called, Then domain also calls the getAllEvents`() = runTest {
        service.getAllSampleData()
        coVerify { domain.getAllSampleData() }
    }

    @Test
    fun `When getSampleData is called, Given the getSampleData call from the domain returns a network error, Then the service returns a network error`() =
        runTest {
            val expected = Resource.Error<SampleDomainData?>(error = "Network Error!")
            coEvery { domain.getSampleData() } returns expected

            val result = service.getSampleData()

            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `When getSampleData is called, Given the getSampleData call from the domain returns a unauthorized error, Then the service returns a unauthorized error`() =
        runTest {
            val expected = Resource.Error<SampleDomainData?>(error = "User is unauthorized!")
            coEvery { domain.getSampleData() } returns expected

            val result = service.getSampleData()

            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `When getSampleData is called, Given the getSampleData call from the domain returns a not-found error, Then the service returns a not-found error`() =
        runTest {
            val expected = Resource.Error<SampleDomainData?>(error = "Page Not Found!")
            coEvery { domain.getSampleData() } returns expected

            val result = service.getSampleData()

            assertThat(result.error).isEqualTo(expected.error)
        }

    @Test
    fun `When getSampleData is called, Given the getSampleData call from the domain returns an unknown error, Then the service returns an unknown error`() =
        runTest {
            val expected = Resource.Error<SampleDomainData?>(error = "Unknown Error!")
            coEvery { domain.getSampleData() } returns expected

            val result = service.getSampleData()

            assertThat(result.error).isEqualTo(expected.error)
        }
}