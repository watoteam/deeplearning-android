package com.chutuan.tukyapp.ui.auth;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.ui.BaseActivity;
import com.chutuan.tukyapp.ui.auth.login.LoginFragment;
import com.chutuan.tukyapp.ui.auth.login.LoginFragment_;
import com.chutuan.tukyapp.ui.auth.register.RegisterFragment;
import com.chutuan.tukyapp.ui.auth.register.RegisterFragment_;
import com.chutuan.tukyapp.ui.dialog.WebviewDialog;
import com.chutuan.tukyapp.ui.main.MainActivity;
import com.chutuan.tukyapp.ui.main.MainActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_auth)
public class AuthActivity extends BaseActivity {
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @AfterViews
    void afterViews(){
        checkLoggedIn();

        if(loginFragment == null){
            loginFragment = LoginFragment_.builder().build();
        }

        if(registerFragment == null){
            registerFragment = RegisterFragment_.builder().build();
        }

        gotoLogin();
    }

    private void checkLoggedIn(){
        boolean isLogged = TuKyApp_.getInstance().getUserPref().isLoggedIn().getOr(false);
        if (isLogged){
            MainActivity_.intent(this).start();
            finish();
        }
    }

    public void gotoRegister(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, registerFragment)
                .disallowAddToBackStack()
                .commit();
    }

    public void gotoLogin(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, loginFragment)
                .disallowAddToBackStack()
                .commit();
    }

    @Click(R.id.tvTuKy)
    void onBtnTuKyClicked(){
        WebViewActivity_.intent(this).start();
    }

    @Click(R.id.tvAboutUs)
    void onBtnAboutUsClicked(){
        WebviewDialog.show(this, null, "file:///android_asset/about_us.html");
    }
}
