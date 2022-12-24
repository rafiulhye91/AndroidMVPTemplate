package com.example.androidmvptemplate.view.main

import android.os.Bundle
import com.example.androidmvptemplate.base.BaseActivity
import com.example.androidmvptemplate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), IMainView {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.start()
    }

    override fun setWelcomeText(string: String?) {
        binding.tvWelcomeText.text = string
    }
}