package com.chutuan.tukyapp.local_data;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface UserSharedPref {
    String currentUser();
    boolean isLoggedIn();
    String accessToken();
}
