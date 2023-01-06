package com.example.androidmvptemplate.base

import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.androidmvptemplate.data.ItemWrapper
import com.example.androidmvptemplate.data.ListItemWrapper
import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.domain.util.NotFoundException
import com.example.androidmvptemplate.domain.util.UnauthorizedException
import com.example.androidmvptemplate.util.TAG
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

open class BaseDomain {
    companion object {
        const val UNKNOWN_ERROR = "Unknown Error!"
        const val NETWORK_ERROR = "Network Error!"
        const val NOT_FOUND = "Page Not Found!"
        const val DB_ERROR = "Database Error!"
        const val UNAUTHORIZED_ERROR = "User is unauthorized!"
    }

    fun <T> handleError(response: Response<T>) {
        when (response.code()) {
            HTTP_UNAUTHORIZED -> throw UnauthorizedException(response.errorBody())
            HTTP_NOT_FOUND -> throw NotFoundException(response.errorBody())
            else -> throw HttpException(response)
        }
    }

    inline fun <T, DTO> handleApiResponse(func: () -> ItemWrapper<T, DTO>): Resource<T> {
        try {
            val item = func()
            if (item.response.isSuccessful) {
                return Resource.Success<T>(item.data)
            }
            handleError(item.response)
        } catch (e: UnauthorizedException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error<T>(error = UNAUTHORIZED_ERROR)
        } catch (e: NotFoundException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error<T>(error = NOT_FOUND)
        } catch (e: HttpException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error<T>(error = NETWORK_ERROR)
        }
        return Resource.Error<T>(error = UNKNOWN_ERROR)
    }

    inline fun <T, DTO> handleApiListResponse(func: () -> ListItemWrapper<T, DTO>): Resource<List<T>> {
        try {
            val item = func()
            if (item.response.isSuccessful) {
                return Resource.Success(item.data as List<T>)
            }
            handleError(item.response)
        } catch (e: UnauthorizedException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = UNAUTHORIZED_ERROR)
        } catch (e: NotFoundException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = NOT_FOUND)
        } catch (e: HttpException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = NETWORK_ERROR)
        }
        return Resource.Error(error = UNKNOWN_ERROR)
    }

    inline fun <T> handleDbListResponse(func: () -> List<T>): Resource<List<T>> {
        return try {
            val data = func()
            Resource.Success<List<T>>(data)
        } catch (e: SQLiteException) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = DB_ERROR)
        } catch (e: Exception) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = UNKNOWN_ERROR)
        }
    }

    inline fun <T> handleDbResponse(func: () -> T): Resource<T> {
        return try {
            val data = func()
            Resource.Success<T>(data)
        } catch (e: SQLiteException) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = DB_ERROR)
        } catch (e: Exception) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = UNKNOWN_ERROR)
        }
    }
}