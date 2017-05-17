package com.chutuan.tukyapp;


import com.chutuan.tukyapp.network.NetworkModule;
import com.chutuan.tukyapp.ui.auth.BaseLoginRegisterFragment;
import com.chutuan.tukyapp.ui.main.MainActivity;
import com.chutuan.tukyapp.ui.main.diagnose.DiagnoseFragment;
import com.chutuan.tukyapp.ui.main.history.HistoryFragment;
import com.chutuan.tukyapp.ui.main.profile.ChangePasswordActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Trieu Tuan on 3/23/2017.
 * Copyright (C) SFR Software.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(BaseLoginRegisterFragment fragment);
    void inject(MainActivity act);
    void inject(HistoryFragment fragment);
    void inject(DiagnoseFragment fragment);
    void inject(ChangePasswordActivity activity);
}
