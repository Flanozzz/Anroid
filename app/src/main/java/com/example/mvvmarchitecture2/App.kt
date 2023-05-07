package com.example.mvvmarchitecture2

import android.app.Application
import com.example.mvvmarchitecture2.model.colors.InMemoryColorsRepository

class App : Application() {
    val models = listOf<Any>(
        InMemoryColorsRepository()
    )
}