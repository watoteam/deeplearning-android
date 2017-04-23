package com.chutuan.tukyapp;


import com.chutuan.tukyapp.network.NetworkModule;
import com.chutuan.tukyapp.ui.auth.login.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Trieu Tuan on 3/23/2017.
 * Copyright (C) SFR Software.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(LoginFragment fragment);
}
