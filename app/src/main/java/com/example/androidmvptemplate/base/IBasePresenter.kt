package com.example.androidmvptemplate.base

interface IBasePresenter {
    fun start()
    fun onResume()
    fun onPause()
    fun onFinish()
}