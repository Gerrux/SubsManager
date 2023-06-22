package com.example.testsubsmanager.di

import android.app.Application
import com.example.testsubsmanager.App
import com.example.testsubsmanager.services.notification.NotificationService
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        UtilsModule::class,
    ]
)
interface AppComponent: AndroidInjector<App> {

    fun inject(notificationService: NotificationService)
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApplication(app: Application): Builder

        fun build(): AppComponent
    }
}