package com.chutuan.tukyapp;

import android.app.Application;

import com.chutuan.tukyapp.local_data.DataSharedPref_;
import com.chutuan.tukyapp.local_data.UserSharedPref_;
import com.chutuan.tukyapp.network.NetworkModule;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;

import lombok.Getter;

@EApplication
public class TuKyApp extends Application {
    @Getter
    AppComponent appComponent;

    @Pref
    @Getter
    UserSharedPref_ userPref;

    @Pref
    @Getter
    DataSharedPref_ dataPref;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }
}
