package com.chutuan.tukyapp.ui.auth;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.ui.BaseActivity;
import com.chutuan.tukyapp.ui.auth.login.LoginFragment;
import com.chutuan.tukyapp.ui.auth.login.LoginFragment_;
import com.chutuan.tukyapp.ui.auth.register.RegisterFragment;
import com.chutuan.tukyapp.ui.auth.register.RegisterFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.activity_auth)
public class AuthActivity extends BaseActivity {
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @AfterViews
    void afterViews(){
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

    public void gotoRegister(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, registerFragment)
                .addToBackStack("register")
                .commit();

    }
}
