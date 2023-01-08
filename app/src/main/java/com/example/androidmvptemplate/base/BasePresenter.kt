package com.example.androidmvptemplate.base

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

abstract class BasePresenter(open val view: IBaseView) : IBasePresenter {

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