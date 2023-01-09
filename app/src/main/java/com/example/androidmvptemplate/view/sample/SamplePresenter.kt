package com.example.androidmvptemplate.view.sample

import com.example.androidmvptemplate.base.BasePresenter
import com.example.androidmvptemplate.data.Resource.Error
import com.example.androidmvptemplate.data.Resource.Success
import com.example.androidmvptemplate.service.sample.ISampleService
import kotlinx.coroutines.launch
import javax.inject.Inject

class SamplePresenter @Inject constructor(
    override val view: ISampleView,
    private val service: ISampleService
) : BasePresenter(view) {

    override fun start() {
        getSampleData()
    }

    private fun getSampleData() {
        view.showProgress()
        presenterScope.launch {
            val result = service.getSampleData()
            view.hideProgress()
            when (result) {
                is Success -> result.data?.let { view.setSampleData(result.data) }
                    ?: view.showError("No data found!")
                is Error -> view.showError(result.error)
            }
        }
    }

    fun getAllData() {
        view.showProgress()
        presenterScope.launch {
            val result = service.getAllSampleData()
            view.hideProgress()
            when (result) {
                is Success -> result.data?.let { view.setAllData(result.data) }
                    ?: view.showError("No data found!")
                is Error -> view.showError(result.error)
            }
        }
    }
}