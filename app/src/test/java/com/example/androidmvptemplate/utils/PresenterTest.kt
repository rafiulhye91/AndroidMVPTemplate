package com.example.androidmvptemplate.utils

import org.junit.Rule

abstract class PresenterTest {
    @get:Rule
    val coroutineRule = MainDispatcherRule()
}