package com.example.newsapplication.ui

import dagger.Module
import dagger.Provides
import java.util.*


@Module
class NewsActivityModule {

    @Provides
    fun provideMyEarningsViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) =
            NewsViewModel(dataManager, schedulerProvider)

    @Provides
    fun provideMyEarningsAdapter(): NewsAdapter {
        return NewsAdapter(ArrayList())
    }
}