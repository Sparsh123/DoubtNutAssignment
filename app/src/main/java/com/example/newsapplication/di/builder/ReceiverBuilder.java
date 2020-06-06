package com.example.newsapplication.di.builder;

import com.tracki.ui.receiver.RestartReceiverModule;
import com.tracki.ui.receiver.ServiceRestartReceiver;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
abstract public class ReceiverBuilder {

    @ContributesAndroidInjector(modules = RestartReceiverModule.class)
    abstract ServiceRestartReceiver bindRestartReceiver();

}
