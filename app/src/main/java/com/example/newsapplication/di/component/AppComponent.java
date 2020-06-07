package com.example.newsapplication.di.component;

import android.app.Application;

import com.example.newsapplication.di.builder.ActivityBuilder;
import com.example.newsapplication.di.module.AppModule;
import com.example.newsapplication.ui.NewsApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class
        })


public interface AppComponent {

    void inject(NewsApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}