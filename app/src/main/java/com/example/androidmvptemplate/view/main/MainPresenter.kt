package com.example.androidmvptemplate.view.main

import com.example.androidmvptemplate.base.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override val view: IMainView
) : BasePresenter(view) {

    override fun start() {
        view.setWelcomeText("Hello World from Android MVP Template!!")
    }
}