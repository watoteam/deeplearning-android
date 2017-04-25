package com.chutuan.tukyapp.ui.auth;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.ui.BaseActivity;
import com.chutuan.tukyapp.ui.auth.login.LoginFragment;
import com.chutuan.tukyapp.ui.auth.login.LoginFragment_;
import com.chutuan.tukyapp.ui.auth.register.RegisterFragment;
import com.chutuan.tukyapp.ui.auth.register.RegisterFragment_;
import com.chutuan.tukyapp.ui.main.MainActivity;
import com.chutuan.tukyapp.ui.main.MainActivity_;

import org.androidannotations.annotations.AfterViews;
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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, loginFragment)
                .disallowAddToBackStack()
                .commit();
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
                .addToBackStack(null)
                .commit();

    }
}
