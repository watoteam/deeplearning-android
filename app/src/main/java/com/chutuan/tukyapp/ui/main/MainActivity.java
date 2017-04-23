package com.chutuan.tukyapp.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.ui.BaseActivity;
import com.chutuan.tukyapp.ui.main.diagnose.DiagnoseFragment;
import com.chutuan.tukyapp.ui.main.diagnose.DiagnoseFragment_;
import com.chutuan.tukyapp.ui.main.history.HistoryFragment;
import com.chutuan.tukyapp.ui.main.history.HistoryFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.main_activity)
public class MainActivity extends BaseActivity {
    private HistoryFragment historyFragment;
    private DiagnoseFragment diagnoseFragment;

    @ViewById(R.id.navMenu)
    NavigationView navMenu;

    @ViewById(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @AfterViews
    void afterViews() {
        if (historyFragment == null) {
            historyFragment = HistoryFragment_.builder().build();
        }

        if (diagnoseFragment == null) {
            diagnoseFragment = DiagnoseFragment_.builder().build();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, diagnoseFragment)
                .disallowAddToBackStack()
                .commit();

        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_diagnose) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, diagnoseFragment)
                            .disallowAddToBackStack()
                            .commit();
                } else if (item.getItemId() == R.id.menu_history) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, historyFragment)
                            .disallowAddToBackStack()
                            .commit();
                }
                return false;
            }
        });
    }
}
