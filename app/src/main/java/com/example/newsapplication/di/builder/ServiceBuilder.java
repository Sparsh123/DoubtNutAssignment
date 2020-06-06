package com.example.newsapplication.di.builder;

import com.tracki.ui.geofencing.GeofenceServiceModule;
import com.tracki.ui.geofencing.GeofenceTransitionsJobIntentService;
import com.tracki.ui.service.sync.SyncService;
import com.tracki.ui.service.sync.SyncServiceModule;
import com.tracki.ui.service.transition.TransitionService;
import com.tracki.ui.service.transition.TransitionServiceModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ServiceBuilder {

    @ContributesAndroidInjector(modules = TransitionServiceModule.class)
    abstract TransitionService bindTransitionService();

    @ContributesAndroidInjector(modules = SyncServiceModule.class)
    abstract SyncService bindSyncService();

    @ContributesAndroidInjector(modules = GeofenceServiceModule.class)
    abstract GeofenceTransitionsJobIntentService bindGeofenceService();
}
