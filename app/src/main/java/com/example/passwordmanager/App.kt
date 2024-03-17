package com.example.passwordmanager

import android.app.Application
import com.example.detail.di.DetailsDepsStore
import com.example.websites.di.UrlsScreenDepsStore

class App: Application() {
    private val appComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        DetailsDepsStore.dependencies = appComponent
        UrlsScreenDepsStore.dependencies = appComponent
    }
}