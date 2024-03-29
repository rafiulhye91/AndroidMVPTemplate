package com.example.androidmvptemplate.mock

import android.app.Application
import android.util.Log
import com.example.androidmvptemplate.data.remote.ApiServices
import com.example.androidmvptemplate.data.remote.model.SampleDTO
import com.example.androidmvptemplate.data.remote.model.SampleErrorDTO
import com.example.androidmvptemplate.util.TAG
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import okio.buffer
import okio.source
import retrofit2.Response
import java.io.FileNotFoundException
import javax.inject.Inject

class MockApiServices @Inject constructor(private val app: Application) :
    ApiServices {
    override suspend fun getSampleData(): Response<SampleDTO?> {
        return app.generateMockResponse("mock/sample_mock_response.json")
    }
}

private const val MOCK_RESPONSE_DELAY: Long = 2000L

private suspend inline fun <reified T> Application.generateMockResponse(
    fileName: String,
    errorFileName: String? = null
): Response<T> {
    val jsonString = getJsonString(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName")
    withContext(Dispatchers.IO) { delay(MOCK_RESPONSE_DELAY) }
    if (!errorFileName.isNullOrEmpty()) {
        return generateErrorResponse(errorFileName)
    }
    return Response.success(
        Gson().fromJson(jsonString, T::class.java)
    )
}

private fun <T> Application.generateErrorResponse(errorFileName: String): Response<T> {
    val jsonString = getJsonString(errorFileName) ?: ""
    val mockError = Gson().fromJson(jsonString, SampleErrorDTO::class.java)
    return Response.error(
        mockError.code, jsonString
            .toResponseBody("application/json".toMediaTypeOrNull())
    )
}

private fun Application.getJsonString(file: String): String? {
    var json: String? = null
    try {
        val source = assets.open(file).source().buffer()
        json = source.readUtf8()
    } catch (ioe: IOException) {
        Log.e(TAG(), "Unable to load json file.")
    } catch (fnfe: FileNotFoundException) {
        Log.e(TAG(), "Specified json file not found: $file")
    }
    return json
}
