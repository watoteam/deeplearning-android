package com.chutuan.tukyapp.utils;


import android.widget.Toast;

import com.chutuan.tukyapp.TuKyApp_;

public class DialogUtils {
    public static void showToast(String msg) {
        Toast.makeText(TuKyApp_.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
}
