package com.example.testsubsmanager.di

import android.app.Application
import android.content.Context
import com.example.testsubsmanager.database.AppDatabaseRepository
import com.example.testsubsmanager.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideAppDatabaseRepository(context: Context): AppDatabaseRepository =
        AppDatabaseRepository.get(context)

    @Singleton
    @Provides
    fun provideViewModel(repository: AppDatabaseRepository): MainViewModel = MainViewModel(repository)

}