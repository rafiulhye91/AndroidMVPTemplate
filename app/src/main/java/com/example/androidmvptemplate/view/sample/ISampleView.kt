package com.example.androidmvptemplate.view.sample

import com.example.androidmvptemplate.base.IBaseView
import com.example.androidmvptemplate.domain.model.SampleDomainData

interface ISampleView : IBaseView {
    fun setSampleData(data: SampleDomainData?)
    fun setAllData(data: List<SampleDomainData?>?)
}