package com.chutuan.tukyapp;

import android.app.Application;

import com.chutuan.tukyapp.local_data.TuKySharedPref_;
import com.chutuan.tukyapp.network.NetworkModule;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import lombok.Getter;
import retrofit2.http.GET;

@EApplication
public class TuKyApp extends Application {
    @Getter
    AppComponent appComponent;

    @Pref
    @Getter
    TuKySharedPref_ pref;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }
}
