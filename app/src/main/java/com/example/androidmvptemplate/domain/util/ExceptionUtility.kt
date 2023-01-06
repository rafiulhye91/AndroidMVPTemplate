package com.example.androidmvptemplate.domain.util

import okhttp3.ResponseBody

class UnauthorizedException(errorBody: ResponseBody?) : Exception()

class NotFoundException(errorBody: ResponseBody?) : Exception()

class UnknownException(errorBody: ResponseBody?) : Exception()