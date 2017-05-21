package com.chutuan.tukyapp.ui.auth;

import android.webkit.WebView;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.ui.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by alext on 21/05/2017.
 */

@EActivity(R.layout.tuky_activity)
public class WebViewActivity extends BaseActivity {
    @ViewById(R.id.webView)
    WebView webView;

    @AfterViews
    void afterViews(){
        setTitle("Tự Kỷ là gì?");
        webView.loadUrl("file:///android_asset/tuki.html");
    }
}
