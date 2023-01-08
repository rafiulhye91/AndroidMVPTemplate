package com.example.androidmvptemplate.view.sample

import com.example.androidmvptemplate.base.IBaseView
import com.example.androidmvptemplate.domain.model.SampleDomainData

interface ISampleView : IBaseView {
    fun showProgress()
    fun hideProgress()
    fun setSampleData(data: SampleDomainData?)
    fun showError(error: String?)
    fun setAllData(data: List<SampleDomainData?>?)
}