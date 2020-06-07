package com.example.newsapplication.di.builder;



import com.example.newsapplication.ui.NewsActivity;
import com.example.newsapplication.ui.NewsActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

       @ContributesAndroidInjector(modules = NewsActivityModule.class)
    abstract NewsActivity bindMainActivity();

       //    @ContributesAndroidInjector(modules = {
//            LeaveActivityModule.class,
//            ApplyLeaveFragmentProvider.class,
//            LeaveHistoryFragmentProvider.class,
//            LeaveSummaryFragmentProvider.class,
//            LeaveApprovalFragmentProvider.class})
//
//    abstract LeaveActivity bindLeaveActivity();

}
