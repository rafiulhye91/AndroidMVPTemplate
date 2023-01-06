package com.example.androidmvptemplate.view.main

import com.example.androidmvptemplate.base.BasePresenter
import com.example.androidmvptemplate.service.sample.SampleService
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override val view: IMainView,
    private val service: SampleService
) : BasePresenter(view) {

    override fun start() {
        view.setWelcomeText("Hello World from Android MVP Template!!")
    }
}