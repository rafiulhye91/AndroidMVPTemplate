package com.example.androidmvptemplate.base

import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.androidmvptemplate.data.ItemWrapper
import com.example.androidmvptemplate.data.ListItemWrapper
import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.util.TAG
import retrofit2.HttpException
import java.io.IOException

open class BaseDomain {

    companion object {
        const val UNKNOWN_ERROR = "Unknown Error!"
    }

    inline fun <T, DTO> handleApiResponse(func: () -> ItemWrapper<T, DTO>): Resource<T> {
        try {
            val item = func()
            if (item.response?.isSuccessful == true) {
                return Resource.Success<T>(item.data)
            }
        } catch (e: HttpException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = e.localizedMessage)
        }
        return Resource.Error(null, UNKNOWN_ERROR)
    }

    inline fun <T, DTO> handleApiListResponse(func: () -> ListItemWrapper<T, DTO>): Resource<List<T>> {
        try {
            val item = func()
            if (item.response?.isSuccessful == true) {
                return Resource.Success<List<T>>(item.data)
            }
        } catch (e: HttpException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            Log.e(TAG(), e.localizedMessage)
            return Resource.Error(error = e.localizedMessage)
        }
        return Resource.Error(null, UNKNOWN_ERROR)
    }

    inline fun <T> handleDbListResponse(func: () -> List<T>): Resource<List<T>> {
        return try {
            val data = func()
            Resource.Success<List<T>>(data)
        } catch (e: SQLiteException) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = e.localizedMessage)
        }
    }

    inline fun <T> handleDbResponse(func: () -> T): Resource<T> {
        return try {
            val data = func()
            Resource.Success<T>(data)
        } catch (e: SQLiteException) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            Log.e(TAG(), e.localizedMessage)
            Resource.Error(error = e.localizedMessage)
        }
    }
}