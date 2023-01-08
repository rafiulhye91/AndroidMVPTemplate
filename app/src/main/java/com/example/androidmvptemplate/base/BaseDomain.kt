package com.example.androidmvptemplate.base

import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.androidmvptemplate.data.ItemWrapper
import com.example.androidmvptemplate.data.ListItemWrapper
import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.data.remote.util.NoDataException
import com.example.androidmvptemplate.data.remote.util.PageNotFoundException
import com.example.androidmvptemplate.data.remote.util.UnauthorizedException
import com.example.androidmvptemplate.util.TAG
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

abstract class BaseDomain {
    companion object {
        const val UNKNOWN_ERROR = "Unknown Error!"
        const val NETWORK_ERROR = "Network Error!"
        const val NOT_FOUND = "Page Not Found!"
        const val DB_ERROR = "Database Error!"
        const val UNAUTHORIZED_ERROR = "User is unauthorized!"
        const val NO_DATA_AVAILABLE = "No Data found!"
    }

    fun <T> handleError(response: Response<T>) {
        when (response.code()) {
            HTTP_UNAUTHORIZED -> throw UnauthorizedException(response.errorBody())
            HTTP_NOT_FOUND -> throw PageNotFoundException(response.errorBody())
            else -> throw HttpException(response)
        }
    }

    inline fun <T, DTO> handleApiResponse(func: () -> ItemWrapper<T, DTO>): Resource<T> {
        try {
            val item = func()
            if (item.response.isSuccessful) {
                if (item.data == null) {
                    throw NoDataException()
                }
                return Resource.Success<T>(item.data)
            }
            handleError(item.response)
        } catch (e: UnauthorizedException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error<T>(error = UNAUTHORIZED_ERROR)
        } catch (e: PageNotFoundException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error<T>(error = NOT_FOUND)
        } catch (e: HttpException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error<T>(error = NETWORK_ERROR)
        } catch (e: NoDataException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = NO_DATA_AVAILABLE)
        }
        return Resource.Error<T>(error = UNKNOWN_ERROR)
    }

    inline fun <T, DTO> handleApiListResponse(func: () -> ListItemWrapper<T, DTO>): Resource<List<T>> {
        try {
            val item = func()
            if (item.response.isSuccessful) {
                if (item.data?.isEmpty() == true) {
                    throw NoDataException()
                }
                return Resource.Success(item.data as List<T>)
            }
            handleError(item.response)
        } catch (e: UnauthorizedException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = UNAUTHORIZED_ERROR)
        } catch (e: PageNotFoundException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = NOT_FOUND)
        } catch (e: HttpException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = NETWORK_ERROR)
        } catch (e: NoDataException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = NO_DATA_AVAILABLE)
        }
        return Resource.Error(error = UNKNOWN_ERROR)
    }

    inline fun <T> handleDbListResponse(func: () -> List<T>): Resource<List<T>> {
        return try {
            val data = func()
            if (data.isEmpty()) throw NoDataException()
            Resource.Success<List<T>>(data)
        } catch (e: SQLiteException) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = DB_ERROR)
        } catch (e: Exception) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = UNKNOWN_ERROR)
        } catch (e: NoDataException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = NO_DATA_AVAILABLE)
        }
    }

    inline fun <T> handleDbResponse(func: () -> T): Resource<T> {
        return try {
            val data = func() ?: throw NoDataException()
            Resource.Success<T>(data)
        } catch (e: SQLiteException) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = DB_ERROR)
        } catch (e: Exception) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = UNKNOWN_ERROR)
        } catch (e: NoDataException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = NO_DATA_AVAILABLE)
        }
    }
}