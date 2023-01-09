package com.example.androidmvptemplate.data.util

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.androidmvptemplate.data.Resource
import com.example.androidmvptemplate.data.util.AppExceptions.*
import com.example.androidmvptemplate.data.util.ErrorType.*
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection.*

private const val TAG = "AppExceptions"

enum class ErrorType(val message: String) {
    UNKNOWN_ERROR("Unknown Error!"),
    NETWORK_ERROR("Network Error!"),
    PAGE_NOT_FOUND("Page Not Found!"),
    DB_ERROR("Database Error!"),
    UNAUTHORIZED_ERROR("User is unauthorized!"),
    NO_DATA_AVAILABLE("No Data found!"),
    FORBIDDEN_ERROR("Server Refused to authorized the request!"),
    BAD_REQUEST_ERROR("Invalid Request!"),
    INTERNAL_SERVER_ERROR("The server encountered an unexpected error!"),
    DB_CONSTRAINT_ERROR("DB constraint is violated!")
}

sealed class AppExceptions : Exception() {
    class UnauthorizedException : AppExceptions()
    class PageNotFoundException : AppExceptions()
    class UnknownException : AppExceptions()
    class NoDataException : AppExceptions()
    class ForBiddenException : AppExceptions()
    class BadRequestException : AppExceptions()
    class InternalServerErrorException : AppExceptions()
}

fun <T> handleHTTPError(response: Response<T>) {
    val errorMap: Map<Int, Exception> = mapOf(
        HTTP_UNAUTHORIZED to UnauthorizedException(),
        HTTP_NOT_FOUND to PageNotFoundException(),
        HTTP_FORBIDDEN to ForBiddenException(),
        HTTP_BAD_REQUEST to BadRequestException(),
        HTTP_INTERNAL_ERROR to InternalServerErrorException(),
    )
    val exception = errorMap[response.code()] ?: HttpException(response)
    throw exception
}

fun <T> handleException(e: Exception): Resource<T> {
    val errorMap: Map<Class<out Exception>, ErrorType> = mapOf(
        UnauthorizedException::class.java to UNAUTHORIZED_ERROR,
        PageNotFoundException::class.java to PAGE_NOT_FOUND,
        HttpException::class.java to NETWORK_ERROR,
        SQLiteException::class.java to DB_ERROR,
        SQLiteConstraintException::class.java to DB_CONSTRAINT_ERROR,
        NoDataException::class.java to NO_DATA_AVAILABLE,
        UnknownException::class.java to UNKNOWN_ERROR,
        ForBiddenException::class.java to FORBIDDEN_ERROR,
        BadRequestException::class.java to BAD_REQUEST_ERROR,
        InternalServerErrorException::class.java to INTERNAL_SERVER_ERROR,
    )
    val error = errorMap[e.javaClass] ?: UNKNOWN_ERROR
    Log.e(TAG, e.localizedMessage)
    return Resource.Error<T>(error = error.message)
}

