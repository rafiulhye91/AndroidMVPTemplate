package com.example.androidmvptemplate.util

inline fun <reified T> T.TAG(): String = T::class.java.simpleName