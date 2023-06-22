package com.example.testsubsmanager

import com.example.testsubsmanager.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
            .bindApplication(this)
            .build()

        // Add this line to inject NotificationService
        appComponent.inject(this)

        return appComponent
    }
}