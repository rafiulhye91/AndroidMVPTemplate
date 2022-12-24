package com.example.androidmvptemplate.base

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

open class BasePresenter @Inject constructor(open val view: IBaseView) : IBasePresenter {

    val presenterScope: CoroutineScope
        get() = view.lifecycleScope

    override fun start() {
        //no-op
    }

    override fun onResume() {
        //no-op
    }

    override fun onPause() {
        //no-op
    }

    override fun onFinish() {
        //no-op
    }
}