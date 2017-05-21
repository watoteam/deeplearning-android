package com.chutuan.tukyapp.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.chutuan.tukyapp.R;

/**
 * Created by alext on 21/05/2017.
 */

public class WebviewDialog extends AlertDialog {
    private WebviewDialog(Context context, String url) {
        super(context);

        View v = LayoutInflater.from(context).inflate(R.layout.webview_dialog, (ViewGroup) null);
        setView(v);

        WebView webView = (WebView) v.findViewById(R.id.dlWebView);
        webView.loadUrl(url);

        setButton(DialogInterface.BUTTON_POSITIVE, "OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
    }

    public static void show(Context ctx, String title, String url) {
        WebviewDialog dialog = new WebviewDialog(ctx, url);
        dialog.setTitle(title);
        dialog.show();
    }
}
