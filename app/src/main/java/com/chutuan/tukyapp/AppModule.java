package com.chutuan.tukyapp;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
    private Application mApp;

    public AppModule(Application app){
        mApp = app;
    }

    @Singleton
    @Provides
    Application provideApplication(){
        return mApp;
    }
}
