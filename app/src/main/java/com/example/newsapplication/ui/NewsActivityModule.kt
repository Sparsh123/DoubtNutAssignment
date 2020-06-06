package com.example.newsapplication.ui

import dagger.Module
import dagger.Provides
import java.util.*


@Module
class NewsActivityModule {

    @Provides
    fun provideNewsViewModel() =
            NewsViewModel()

    @Provides
    fun provideNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }
}