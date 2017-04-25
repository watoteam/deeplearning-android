package com.chutuan.tukyapp.local_data;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Trieu Tuan on 4/25/2017.
 * Copyright (C) SFR Software.
 */

@SharedPref
public interface DataSharedPref {
    String symptoms();
}
