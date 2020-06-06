package com.example.newsapplication.ui

import dagger.Module
import dagger.Provides
import java.util.*


@Module
class NewsActivityModule {

    @Provides
    fun provideMyEarningsViewModel() =
            NewsViewModel()

    @Provides
    fun provideNewsAdapter(): NewsAdapter {
        return NewsAdapter(ArrayList())
    }
}