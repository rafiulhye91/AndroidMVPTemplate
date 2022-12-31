package com.example.androidmvptemplate.base

import android.database.sqlite.SQLiteException
import com.example.androidmvptemplate.data.ItemWrapper
import com.example.androidmvptemplate.data.ListItemWrapper
import com.example.androidmvptemplate.data.Resource
import retrofit2.HttpException
import java.io.IOException

open class BaseDomain {
    inline fun <T, DTO> handleApiResponse(func: () -> ItemWrapper<T, DTO>): Resource<T>? {
        try {
            val item = func()
            if (item.response?.isSuccessful == true) {
                return Resource.Success<T>(item.data)
            }
        } catch (e: HttpException) {
            return Resource.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            return Resource.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            return Resource.Error(error = e.localizedMessage)
        }
        return null
    }

    inline fun <T, DTO> handleApiListResponse(func: () -> ListItemWrapper<T, DTO>): Resource<List<T>>? {
        try {
            val item = func()
            if (item.response?.isSuccessful == true) {
                return Resource.Success<List<T>>(item.data)
            }
        } catch (e: HttpException) {
            return Resource.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            return Resource.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            return Resource.Error(error = e.localizedMessage)
        }
        return null
    }

    inline fun <T> handleDbListResponse(func: () -> List<T>): Resource<List<T>> {
        return try {
            val items = func()
            Resource.Success<List<T>>(items)
        } catch (e: SQLiteException) {
            Resource.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            Resource.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            Resource.Error(error = e.localizedMessage)
        }
    }

    inline fun <T> handleDbResponse(func: () -> T): Resource<T> {
        return try {
            val item = func()
            Resource.Success<T>(item)
        } catch (e: SQLiteException) {
            Resource.Error(error = e.localizedMessage)
        } catch (e: IOException) {
            Resource.Error(error = e.localizedMessage)
        } catch (e: Exception) {
            Resource.Error(error = e.localizedMessage)
        }
    }
}