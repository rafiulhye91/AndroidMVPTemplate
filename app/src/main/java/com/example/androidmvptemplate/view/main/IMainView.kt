package com.example.androidmvptemplate.view.main

import com.example.androidmvptemplate.base.IBaseView

interface IMainView : IBaseView {
    fun setWelcomeText(string: String?)
}