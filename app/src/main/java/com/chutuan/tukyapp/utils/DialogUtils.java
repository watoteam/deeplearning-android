package com.chutuan.tukyapp.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.chutuan.tukyapp.TuKyApp_;

public class DialogUtils {
    private static ProgressDialog pDialog;

    public static void showToast(String msg) {
        Toast.makeText(TuKyApp_.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showMessageDialog(Context ctx, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public static void showMessageDialog(Context ctx, String message) {
        showMessageDialog(ctx, null, message);
    }

    public static void showProgressDialog(Context ctx, String message) {
        dismissProgressDialog();
        pDialog = new ProgressDialog(ctx);
        pDialog.setCancelable(false);
        pDialog.setMessage(message);
        pDialog.show();
    }

    public static void dismissProgressDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
