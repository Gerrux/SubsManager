package com.example.testsubsmanager.di

import com.example.testsubsmanager.ui.AddSubscriptionFragment
import com.example.testsubsmanager.ui.AnalyticsFragment
import com.example.testsubsmanager.ui.HomeFragment
import com.example.testsubsmanager.ui.NotificationsFragment
import com.example.testsubsmanager.ui.SettingsFragment
import com.example.testsubsmanager.ui.SplashScreenFragment
import com.example.testsubsmanager.ui.SubscriptionDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashScreenFragment(): SplashScreenFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeAnalyticsFragment(): AnalyticsFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsFragment(): NotificationsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddSubscriptionFragment(): AddSubscriptionFragment

    @ContributesAndroidInjector
    abstract fun contributeSubscriptionDetailFragment(): SubscriptionDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

}