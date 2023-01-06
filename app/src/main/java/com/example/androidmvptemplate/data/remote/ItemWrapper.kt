package com.example.androidmvptemplate.data

import retrofit2.Response

data class ItemWrapper<T, DTO>(
    val data: T?,
    val response: Response<DTO?>
)

data class ListItemWrapper<T, DTO>(
    val data: List<T?>,
    val response: Response<List<DTO?>>
)
