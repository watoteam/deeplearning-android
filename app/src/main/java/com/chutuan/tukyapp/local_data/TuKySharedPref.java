package com.chutuan.tukyapp.local_data;

import com.chutuan.tukyapp.model.User;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface TuKySharedPref {
    String currentUser();
}
