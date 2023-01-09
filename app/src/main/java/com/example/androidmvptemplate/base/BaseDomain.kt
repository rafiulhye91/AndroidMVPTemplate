package com.example.androidmvptemplate.base

import com.example.androidmvptemplate.data.ItemWrapper
import com.example.androidmvptemplate.data.ListItemWrapper
import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.data.util.AppExceptions.NoDataException
import com.example.androidmvptemplate.data.util.ErrorType.UNKNOWN_ERROR
import com.example.androidmvptemplate.data.util.handleException
import com.example.androidmvptemplate.data.util.handleHTTPError

abstract class BaseDomain {
    inline fun <T, DTO> handleApiResponse(func: () -> ItemWrapper<T, DTO>): Resource<T> {
        try {
            val item = func()
            if (item.response.isSuccessful) {
                if (item.data == null) {
                    throw NoDataException()
                }
                return Resource.Success(item.data)
            }
            handleHTTPError(item.response)
        } catch (e: Exception) {
            return handleException(e)
        }
        return Resource.Error(error = UNKNOWN_ERROR.message)
    }


    inline fun <T, DTO> handleApiListResponse(func: () -> ListItemWrapper<T, DTO>): Resource<List<T>> {
        try {
            val item = func()
            if (item.response.isSuccessful) {
                if (item.data == null || item.data.isEmpty()) {
                    throw NoDataException()
                }
                return Resource.Success(item.data as List<T>)
            }
            handleHTTPError(item.response)
        } catch (e: Exception) {
            return handleException(e)
        }
        return Resource.Error(error = UNKNOWN_ERROR.message)
    }

    inline fun <T> handleDbResponse(func: () -> T?): Resource<T> {
        return try {
            val data = func() ?: throw NoDataException()
            Resource.Success(data)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}