package com.goldbach.absolutecinema

import android.app.Application
import com.goldbach.absolutecinema.data.AppContainer
import com.goldbach.absolutecinema.data.DefaultAppContainer

class MovieApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}