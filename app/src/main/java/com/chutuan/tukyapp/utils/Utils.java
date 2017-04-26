package com.chutuan.tukyapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Trieu Tuan on 4/26/2017.
 * Copyright (C) SFR Software.
 */

public class Utils {
    public static void hideKeyboard(Activity act) {
        // Check if no view has focus:
        View view = act.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String formatTimestamp(String timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
            output.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            Date date = sdf.parse(timestamp);
            return output.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
