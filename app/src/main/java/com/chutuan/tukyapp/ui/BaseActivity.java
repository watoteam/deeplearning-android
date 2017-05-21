package com.chutuan.tukyapp.ui;


import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.utils.DialogUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public abstract class BaseActivity extends AppCompatActivity {
    @Nullable
    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;

    @AfterViews
    protected void afterBaseView() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isPressedBackTwice = false;

    @Override
    public void onBackPressed() {
        if(isTaskRoot()){
            if (isPressedBackTwice) {
                finish();
            } else {
                DialogUtils.showToast("Nhấn BACK lần nữa để thoát.");
                isPressedBackTwice = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isPressedBackTwice = false;
                    }
                }, 3 * 1000);
            }
            return;
        }
        super.onBackPressed();
    }
}
