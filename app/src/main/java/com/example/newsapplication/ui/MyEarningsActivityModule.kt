package com.tracki.ui.earnings

import dagger.Module
import dagger.Provides
import java.util.*


@Module
class MyEarningsActivityModule {

    @Provides
    fun provideMyEarningsViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) =
            MyEarningsViewModel(dataManager, schedulerProvider)

    @Provides
    fun provideMyEarningsAdapter(): MyEarningsAdapter {
        return MyEarningsAdapter(ArrayList())
    }
}