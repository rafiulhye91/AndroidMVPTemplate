package com.example.androidmvptemplate.view.sample

import com.example.androidmvptemplate.base.BaseActivity
import com.example.androidmvptemplate.domain.model.SampleDomainData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : BaseActivity(), ISampleView {

    override fun showProgress() {
        //TODO("Not yet implemented")
    }

    override fun hideProgress() {
        //TODO("Not yet implemented")
    }

    override fun setSampleData(data: SampleDomainData?) {
        //TODO("Not yet implemented")
    }

    override fun showError(error: String?) {
        //TODO("Not yet implemented")
    }

    override fun setAllData(data: List<SampleDomainData?>?) {
        //TODO("Not yet implemented")
    }
}