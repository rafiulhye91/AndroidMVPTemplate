package com.example.androidmvptemplate.view.main

import android.os.Bundle
import com.example.androidmvptemplate.R
import com.example.androidmvptemplate.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}