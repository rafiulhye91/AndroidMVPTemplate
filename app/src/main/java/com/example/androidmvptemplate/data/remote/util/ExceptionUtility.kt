package com.example.androidmvptemplate.data.remote.util

import okhttp3.ResponseBody

class UnauthorizedException(errorBody: ResponseBody?) : Exception()

class PageNotFoundException(errorBody: ResponseBody?) : Exception()

class UnknownException(errorBody: ResponseBody?) : Exception()

class NoDataException : Exception()