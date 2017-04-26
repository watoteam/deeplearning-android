package com.chutuan.tukyapp.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.TextView;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.User;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.services.ApiService;
import com.chutuan.tukyapp.ui.BaseActivity;
import com.chutuan.tukyapp.ui.auth.AuthActivity_;
import com.chutuan.tukyapp.ui.main.diagnose.DiagnoseFragment;
import com.chutuan.tukyapp.ui.main.diagnose.DiagnoseFragment_;
import com.chutuan.tukyapp.ui.main.history.HistoryFragment;
import com.chutuan.tukyapp.ui.main.history.HistoryFragment_;
import com.chutuan.tukyapp.utils.DialogUtils;
import com.chutuan.tukyapp.utils.GsonUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@EActivity(R.layout.main_activity)
public class MainActivity extends BaseActivity {
    private HistoryFragment historyFragment;
    private DiagnoseFragment diagnoseFragment;

    private TextView tvName;
    private TextView tvEmail;

    @ViewById(R.id.navMenu)
    NavigationView navMenu;

    @ViewById(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Inject
    ApiService apiService;

    @AfterViews
    void afterViews() {
        TuKyApp_.getInstance().getAppComponent().inject(this);

        tvName = (TextView) navMenu.getHeaderView(0).findViewById(R.id.tvName);
        tvEmail = (TextView) navMenu.getHeaderView(0).findViewById(R.id.tvMail);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);

        if (historyFragment == null) {
            historyFragment = HistoryFragment_.builder().build();
        }

        if (diagnoseFragment == null) {
            diagnoseFragment = DiagnoseFragment_.builder().build();
        }

        replaceFragment(diagnoseFragment);
        navMenu.getMenu().getItem(0).setChecked(true);
        setTitle("Chẩn đoán");

        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_diagnose) {
                    replaceFragment(diagnoseFragment);
                    item.setChecked(true);
                    setTitle("Chẩn đoán");
                } else if (item.getItemId() == R.id.menu_history) {
                    replaceFragment(historyFragment);
                    item.setChecked(true);
                    setTitle("Lịch sử");
                } else if (item.getItemId() == R.id.menu_logout) {
                    doLogout();
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
        displayProfile();
        drawerToggle.syncState();
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .disallowAddToBackStack()
                .commit();
    }

    private void doLogout() {
        String accessToken = TuKyApp_.getInstance().getUserPref().accessToken().getOr(null);
        DialogUtils.showProgressDialog(this, "Đăng đăng xuất...");
        apiService.logout(accessToken).enqueue(new Callback<ResponseWrapper<Object>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<Object>> call, Response<ResponseWrapper<Object>> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    TuKyApp_.getInstance().getUserPref().edit().clear().apply();
                    AuthActivity_.intent(MainActivity.this).start();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<Object>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
            }
        });
    }

    private void displayProfile() {
        String userJson = TuKyApp_.getInstance().getUserPref().currentUser().getOr(null);
        if (userJson == null) {
            return;
        }
        User user = GsonUtils.fromJson(userJson, User.class);
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
    }
}
